package com.dev.bookly.scheduling.repository.impl;

import com.dev.bookly.scheduling.repository.ScheduleOwnershipRepository;
import com.dev.bookly.security.services.UserDetailsImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ScheduleOwnershipRepositoryMySQLImpl implements ScheduleOwnershipRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleOwnershipRepositoryMySQLImpl(DataSource dataSource ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public boolean userOwnsBusiness(Long businessId, Long userId) {

        //Checking if this is the Admin
        UserDetailsImpl currentUser = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.isAdmin()) {
            return true;
        }

        String sql = """
            SELECT COUNT(*)
            FROM businesses b
            JOIN accounts a ON b.user_id = a.user_id
            WHERE b.id = ? AND a.user_id = ?
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, businessId, userId);
        return count != null && count > 0;
    }

    @Override
    public boolean userOwnsBusinessShift(Long businessId, Long shiftId, Long userId) {

        //Checking if this is the Admin
        UserDetailsImpl currentUser = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.isAdmin()) {
            return true;
        }

        String sql = """
                    SELECT COUNT(*)
                    FROM business_shifts shift
                    JOIN businesses b ON shift.business_id = b.id
                    JOIN  accounts a ON b.user_id = a.user_id
                    WHERE shift.id = ? AND b.id = ? AND a.user_id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, shiftId  , businessId, userId);
        return count != null && count > 0;
    }

    @Override
    public boolean userOwnsResource(Long businessId, Long serviceId, Long resourceId, Long userId) {

        //Checking if this is the Admin
        UserDetailsImpl currentUser = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.isAdmin()) {
            return true;
        }

        String sql = """
            SELECT COUNT(*)
            FROM resources r
            JOIN services s ON r.service_id = s.id
            JOIN businesses b ON s.business_id = b.id
            JOIN accounts a ON b.user_id = a.user_id
            WHERE r.id = ? AND s.id = ? AND b.id = ? AND a.user_id = ?
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, resourceId, serviceId, businessId, userId);
        return count != null && count > 0;
    }

    @Override
    public boolean userOwnsResourceShift(Long businessId, Long serviceId, Long resourceId, Long shiftId, Long userId) {

        //Checking if this is the Admin
        UserDetailsImpl currentUser = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.isAdmin()) {
            return true;
        }

        String sql = """
            SELECT COUNT(*)
            FROM resource_shifts shift
            JOIN resources r ON shift.resource_id = r.id
            JOIN services s ON r.service_id = s.id
            JOIN businesses b ON s.business_id = b.id
            JOIN accounts a ON b.user_id = a.user_id
            WHERE shift.id = ? AND r.id = ? AND s.id = ? AND b.id = ? AND a.user_id = ?
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, shiftId , resourceId, serviceId, businessId, userId);
        return count != null && count > 0;
    }
}
