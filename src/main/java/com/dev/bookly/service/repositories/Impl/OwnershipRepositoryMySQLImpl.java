package com.dev.bookly.service.repositories.Impl;

import com.dev.bookly.service.repositories.OwnershipRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository()
public class OwnershipRepositoryMySQLImpl implements OwnershipRepository {

    private JdbcTemplate jdbcTemplate;

    public OwnershipRepositoryMySQLImpl(DataSource dataSource ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean userOwnsBusiness(Long businessId, String username) {
        String sql = """
            SELECT COUNT(*) 
            FROM businesses b
            JOIN accounts a ON b.user_id = a.user_id
            WHERE b.id = ? AND a.username = ?
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, businessId, username);
        return count != null && count > 0;
    }

    @Override
    public boolean userOwnsService(Long businessId, Long serviceId, String username) {
        String sql = """
            SELECT COUNT(*) 
            FROM services s
            JOIN businesses b ON s.business_id = b.id
            JOIN accounts a ON b.user_id = a.user_id
            WHERE s.id = ? AND b.id = ? AND a.username = ?
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, serviceId, businessId, username);
        return count != null && count > 0;
    }

    @Override
    public boolean userOwnsResource(Long businessId, Long serviceId, Long resourceId, String username) {
        String sql = """
            SELECT COUNT(*) 
            FROM resources r
            JOIN services s ON r.service_id = s.id
            JOIN businesses b ON s.business_id = b.id
            JOIN accounts a ON b.user_id = a.user_id
            WHERE r.id = ? AND s.id = ? AND b.id = ? AND a.username = ?
        """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, resourceId, serviceId, businessId, username);
        return count != null && count > 0;
    }
}