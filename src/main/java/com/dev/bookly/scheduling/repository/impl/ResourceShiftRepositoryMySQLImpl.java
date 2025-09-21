package com.dev.bookly.scheduling.repository.impl;

import com.dev.bookly.scheduling.domains.ResourceShift;
import com.dev.bookly.scheduling.repository.ResourceShiftRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ResourceShiftRepositoryMySQLImpl implements ResourceShiftRepository {

    private JdbcTemplate jdbcTemplate;


    public ResourceShiftRepositoryMySQLImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ResourceShift> findByResourceId(Long resourceId) {
        String query =
                """
                    SELECT * FROM resource_shifts WHERE resource_id = ?;
                """;

        List<ResourceShift> shifts = jdbcTemplate.query(query, (rs, rowNum) -> {

            LocalDate effectiveTo = rs.getDate("effective_to") != null
                    ? rs.getDate("effective_to").toLocalDate()
                    : null;

            return new ResourceShift(
                    rs.getLong("id"),
                    rs.getLong("resource_id"),
                    rs.getShort("slot_no"),
                    rs.getShort("day_of_week"),
                    rs.getTime("start_time").toLocalTime(),
                    rs.getTime("end_time").toLocalTime(),
                    rs.getDate("effective_from").toLocalDate(),
                    effectiveTo,
                    rs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }, resourceId);

        return shifts;
    }

    @Override
    public List<ResourceShift> findEffectiveResourceAndDate(Long resourceId, LocalDate date) {
        String query = """
        SELECT * FROM resource_shifts
        WHERE resource_id = ?
          AND day_of_week = ?
          AND effective_from <= ?
          AND (effective_to IS NULL OR effective_to >= ?)
    """;

        return jdbcTemplate.query(query, (rs, rowNum) -> new ResourceShift(
                rs.getLong("id"),
                rs.getLong("resource_id"),
                rs.getShort("slot_no"),
                rs.getShort("day_of_week"),
                rs.getTime("start_time").toLocalTime(),
                rs.getTime("end_time").toLocalTime(),
                rs.getDate("effective_from").toLocalDate(),
                rs.getDate("effective_to") != null ? rs.getDate("effective_to").toLocalDate() : null,
                rs.getTimestamp("updated_at").toLocalDateTime(),
                rs.getTimestamp("created_at").toLocalDateTime()
        ), resourceId, (short) date.getDayOfWeek().getValue(), date, date);
    }

    @Override
    public Optional<ResourceShift> save(ResourceShift resourceShift) {

        String query =
                """
                  insert into resource_shifts(resource_id , day_of_week , slot_no , start_time , end_time , effective_from , effective_to) values (? ,? , ?, ? ,?, ? , ?);  
                """;
        jdbcTemplate.update(query,resourceShift.getResourceId() , resourceShift.getDayOfWeek() , resourceShift.getSlotNo() , resourceShift.getStartTime()
                , resourceShift.getEndTime(), resourceShift.getEffectiveFrom(), resourceShift.getEffectiveTo());

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return findResourceShiftById(resourceShift.getResourceId(), id);
    }

    @Override
    public int update(Long shiftId, ResourceShift resourceShift) {
        String query =
                """
                   update resource_shifts set day_of_week = ? , slot_no = ?, start_time = ? , end_time = ?, effective_from = ? , effective_to = ?
                    where id = ? AND resource_id = ? ;
                """;
        return jdbcTemplate.update(query,
                resourceShift.getDayOfWeek(),
                resourceShift.getSlotNo(),
                resourceShift.getStartTime(),
                resourceShift.getEndTime(),
                resourceShift.getEffectiveFrom(),
                resourceShift.getEffectiveTo(),
                shiftId,
                resourceShift.getResourceId()
        );
    }

    @Override
    public int delete(Long resourceId, Long shiftId) {
        String query =
                """
                   delete from resource_shifts where id = ? AND resource_id = ? ;
                """;
        return jdbcTemplate.update(query, shiftId , resourceId);
    }

    @Override
    public boolean existsOverlap(Long resourceId, short dayOfWeek, LocalTime startTime, LocalTime endTime) {
        String query = """
        SELECT COUNT(*)
        FROM resource_shifts
        WHERE resource_id = ?
          AND day_of_week = ?
          AND (
               (start_time < ? AND end_time > ?)   -- existing spans new start
            OR (start_time < ? AND end_time > ?)   -- existing spans new end
            OR (start_time >= ? AND end_time <= ?) -- existing inside new shift
            OR (start_time <= ? AND end_time >= ?) -- new shift fully covers existing
          )
    """;

        Integer count = jdbcTemplate.queryForObject(query, Integer.class,
                resourceId, dayOfWeek,
                endTime, startTime,
                endTime, startTime,
                startTime, endTime,
                startTime, endTime
        );

        return count != null && count > 0;
    }

    @Override
    public boolean existsOverlapExcludingId(Long shiftId, Long resourceId, short dayOfWeek, LocalTime startTime, LocalTime endTime) {
        String query = """
        SELECT COUNT(*)
        FROM resource_shifts
        WHERE resource_id = ?
          AND day_of_week = ?
          AND id <> ? -- exclude current shift
          AND (
               (start_time < ? AND end_time > ?)   -- existing spans new start
            OR (start_time < ? AND end_time > ?)   -- existing spans new end
            OR (start_time >= ? AND end_time <= ?) -- existing inside new shift
            OR (start_time <= ? AND end_time >= ?) -- new shift fully covers existing
          )
    """;

        Integer count = jdbcTemplate.queryForObject(query, Integer.class,
                resourceId, dayOfWeek, shiftId,
                endTime, startTime,
                endTime, startTime,
                startTime, endTime,
                startTime, endTime
        );

        return count != null && count > 0;
    }

    @Override
    public Optional<ResourceShift> findResourceShiftById(Long resourceId, Long shiftId) {
        String query =
                """
                SELECT * FROM resource_shifts
                WHERE id = ? AND resource_id = ?;
                """;

        List<ResourceShift> result = jdbcTemplate.query(query, (rs, rowNum) -> {

            LocalDate effectiveTo = rs.getDate("effective_to") != null
                    ? rs.getDate("effective_to").toLocalDate()
                    : null;

            return new ResourceShift(
                    rs.getLong("id"),
                    rs.getLong("resource_id"),
                    rs.getShort("slot_no"),
                    rs.getShort("day_of_week"),
                    rs.getTime("start_time").toLocalTime(),
                    rs.getTime("end_time").toLocalTime(),
                    rs.getDate("effective_from").toLocalDate(),
                    effectiveTo,
                    rs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }, shiftId, resourceId);

        return result.stream().findFirst();
    }
}
