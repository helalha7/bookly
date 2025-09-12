package com.dev.bookly.businessProfile.repostries.impl;

import com.dev.bookly.businessProfile.domains.Business;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTO;
import com.dev.bookly.businessProfile.repostries.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class BusinessRepositoryMysqlImpl  implements BusinessRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BusinessRepositoryMysqlImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


//    @Override
//    public Business findById(Long userId, Long businessId) {
//        String sql = "SELECT * FROM businesses WHERE user_id=? AND id=?";
//        List<Business> list = jdbcTemplate.query(
//                sql,
//                (rs, rowNum) -> new Business(
//                        rs.getLong("id"),
//                        rs.getLong("user_id"),
//                        rs.getString("name"),
//                        rs.getString("address"),
//                        rs.getString("logo_url"),
//                        rs.getString("description"),
//                        rs.getString("timezone"),
//                        rs.getBoolean("active"),
//                        rs.getDate("created_at"),
//                        rs.getDate("updated_at")
//                ),
//                userId, businessId
//        );
//
//        return list.isEmpty() ? null : list.get(0);
//    }

    @Override
    public Business save(Business b) {
        String sql = "INSERT INTO businesses (user_id, name, address, logo_url, description, timezone) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                b.getUserId(),
                b.getName(),
                b.getAddress(),
                b.getLogoUrl(),
                b.getDescription(),
                b.getTimeZone()
        );

        Long user_id = b.getUserId();

        return jdbcTemplate.queryForObject(
                "SELECT * FROM businesses WHERE user_id = ?",
                (rs, rowNum) -> new Business(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("logo_url"),
                        rs.getString("description"),
                        rs.getString("timezone"),
                        rs.getBoolean("active"),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at")
                ),
                user_id
        );
    }


    @Override
    public List<Business> findAll(Long userId) {
        String query = "select * from businesses where user_id=?";

        List<Business> businesses = jdbcTemplate.query(
                query,
                (rs, rowNum) -> new Business(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("logo_url"),
                        rs.getString("description"),
                        rs.getString("timezone"),
                        rs.getBoolean("active"),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at")
                ),
                userId
        );

        return businesses;
    }

    @Override
    public Business update(Business b) {
        final String sql = """
        UPDATE businesses
           SET name = ?,
               address = ?,
               logo_url = ?,
               description = ?,
               timezone = ?,
               active = ?
         WHERE id = ? AND user_id = ?
        """;

        int rows = jdbcTemplate.update(sql,
                b.getName(),
                b.getAddress(),
                b.getLogoUrl(),
                b.getDescription(),
                b.getTimeZone(),
                b.isActive(),
                b.getId(),
                b.getUserId()
        );

        if (rows == 0) {
            throw new IllegalStateException("Business not found or not owned by user");
        }


        return jdbcTemplate.queryForObject(
                "SELECT * FROM businesses WHERE id = ?",
                (rs, rowNum) -> new Business(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("logo_url"),
                        rs.getString("description"),
                        rs.getString("timezone"),
                        rs.getBoolean("active"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ),
                b.getId()
        );
    }


    @Override
    public Business status(Long id, boolean active,Long userId) {

        String sql = "UPDATE businesses SET active = ? WHERE user_id = ? AND id = ?";

        int rows = jdbcTemplate.update(sql, active, userId, id);
        if (rows == 0) {
            throw new IllegalStateException("Business not found or not owned by user");
        }

        return jdbcTemplate.queryForObject(
                "select * from businesses where id = ?",
                (rs,rowNum) -> new Business(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("logo_url"),
                        rs.getString("description"),
                        rs.getString("timezone"),
                        rs.getBoolean("active"),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at")
                ),
                id
        );



    }


}
