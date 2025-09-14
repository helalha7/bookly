package com.dev.bookly.businessProfile.repostries.impl;

import com.dev.bookly.businessProfile.domains.Business;
import com.dev.bookly.businessProfile.dtos.response.BusinessResponseDTOAdmin;
import com.dev.bookly.businessProfile.exceptions.BusinessNotFoundException;
import com.dev.bookly.businessProfile.repostries.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BusinessRepositoryMysqlImpl  implements BusinessRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BusinessRepositoryMysqlImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Business findById(Long businessId) {
        String sql = "SELECT * FROM businesses WHERE id=?";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
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
                    businessId
            );
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new BusinessNotFoundException("Business with id " + businessId + " not found");
        }
    }


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
         WHERE id = ?
        """;

        int rows = jdbcTemplate.update(sql,
                b.getName(),
                b.getAddress(),
                b.getLogoUrl(),
                b.getDescription(),
                b.getTimeZone(),
                b.isActive(),
                b.getId()

        );

        if (rows == 0) {
            throw new BusinessNotFoundException("The business with id " + b.getId() + " not found");
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
            throw new BusinessNotFoundException("the business with id " + id + " not found");
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



    @Override
    public Long delete(Long id){
        String sql = "DELETE FROM businesses WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows == 0) {
            throw new BusinessNotFoundException("the business with id " + id + " not found");
        }

        return id;
    }

    @Override
    public List<Business> getAllUsersBusinesses() {

        String query = "select * from businesses";
        List<Business> businesses = jdbcTemplate.query(query,
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
                )
                );


        return businesses;
    }

    @Override
    public Business getBusinessByUserId(Long userId) {

        String query = "SELECT * FROM businesses WHERE user_id = ?";

        try {
            return jdbcTemplate.queryForObject(
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
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new BusinessNotFoundException("No business found for user with id " + userId);
        }




    }


}
