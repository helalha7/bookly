package com.dev.bookly.scheduling.repository.impl;

import com.dev.bookly.scheduling.domains.BusinessShift;
import com.dev.bookly.scheduling.repository.BusinessShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BusinessShiftRepositoryImpl implements BusinessShiftRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<BusinessShift> findByBusinessId(Long businessId) {
        String query =
                """
                    SELECT * FROM business_shifts WHERE business_id = ?;
                """;

        List<BusinessShift> shifts = jdbcTemplate.query(query, (rs, rowNum) -> {
            return new BusinessShift(
                    rs.getLong("id"),
                    rs.getLong("business_id"),
                    rs.getShort("slot_no"),
                    rs.getShort("day_of_week"),
                    rs.getTime("start_time").toLocalTime(),
                    rs.getTime("end_time").toLocalTime()
            );
        }, businessId);

        return shifts;
    }

    @Override
    public BusinessShift save(BusinessShift businessShift) {
        String query =
                """
                  insert into business_shifts(business_id , slot_no , day_of_week , start_time , end_time) values (? ,? , ?, ? ,?);  
                """;
        jdbcTemplate.update(query,businessShift.getBusinessId() , businessShift.getSlotNo() , businessShift.getDayOfWeek() , businessShift.getStartTime() , businessShift.getEndTime());
//        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
//        businessShift.setId(id);
        return businessShift;
    }

    @Override
    public BusinessShift update(Long shiftId, BusinessShift businessShift) {
        String query =
                """
                   update business_shifts set slot_no = ? , start_time = ? , end_time = ? where id = ? AND business_id = ? ;     
                """;
        jdbcTemplate.update(query,businessShift.getSlotNo() , businessShift.getStartTime() , businessShift.getEndTime() , shiftId ,businessShift.getBusinessId());
//        businessShift.setId(shiftId);
        return businessShift;
    }

    @Override
    public void delete(Long shiftId, Long businessId) {
        String query =
                """
                   delete from business_shifts where id = ? AND business_id = ? ;    
                """;
        jdbcTemplate.update(query, shiftId , businessId);
    }
}
