package com.dev.bookly.booking.repositories.Impl;

import com.dev.bookly.booking.domains.Booking;
import com.dev.bookly.booking.domains.EBookingSource;
import com.dev.bookly.booking.domains.EBookingStatus;
import com.dev.bookly.booking.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository("BookingRepoMySQL")
public class BookingRepositoryMySQLImpl implements BookingRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public BookingRepositoryMySQLImpl(DataSource ds) {
        this.jdbc = new JdbcTemplate(ds);
    }

    private static final RowMapper<Booking> MAPPER = new RowMapper<>() {
        @Override public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Booking(
                    rs.getLong("id"),
                    rs.getLong("client_id"),
                    rs.getLong("resource_id"),
                    rs.getLong("resource_shift_id"),
                    rs.getTimestamp("start_time").toInstant(),
                    rs.getTimestamp("end_time").toInstant(),
                    EBookingStatus.valueOf(rs.getString("status")),
                    EBookingSource.valueOf(rs.getString("source")),
                    rs.getLong("policy_id"),
                    rs.getTimestamp("created_at").toInstant()
            );
        }
    };

    @Override
    public Optional<Booking> findById(Long id) {
        List<Booking> list = jdbc.query("SELECT * FROM bookings WHERE id = ?", MAPPER, id);
        return list.stream().findFirst();
    }

    @Override
    public List<Booking> findOverlapping(Long resourceId, Instant start, Instant end) {
        final String sql = """
            SELECT * FROM bookings
            WHERE resource_id = ?
              AND status <> 'CANCELED'
              AND NOT (end_time <= ? OR start_time >= ?)
            """;
        return jdbc.query(sql, MAPPER,
                resourceId,
                java.sql.Timestamp.from(start),
                java.sql.Timestamp.from(end));
    }

    @Override
    public Booking save(Booking b) {
        if (b.id() == null) {
            final String ins = """
                INSERT INTO bookings (client_id, resource_id, resource_shift_id, start_time, end_time, status, source, policy_id, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
            jdbc.update(ins,
                    b.clientId(), b.resourceId(), b.resourceShiftId(),
                    java.sql.Timestamp.from(b.startTime()),
                    java.sql.Timestamp.from(b.endTime()),
                    b.status().name(), b.source().name(), b.policyId(),
                    java.sql.Timestamp.from(b.createdAt()));
            Long id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
            return new Booking(id, b.clientId(), b.resourceId(), b.resourceShiftId(),
                    b.startTime(), b.endTime(), b.status(), b.source(), b.policyId(), b.createdAt());
        } else {
            final String upd = """
                UPDATE bookings
                   SET start_time = ?, end_time = ?, status = ?
                 WHERE id = ?
                """;
            jdbc.update(upd,
                    java.sql.Timestamp.from(b.startTime()),
                    java.sql.Timestamp.from(b.endTime()),
                    b.status().name(),
                    b.id());
            return b;
        }
    }
}

