package com.dev.bookly.role.repositories.impl;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.role.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class RoleRepositoryMySQLImpl implements RoleRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepositoryMySQLImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Role getRoleByName(String name) {
        String query =
        """
            SELECT * FROM roles WHERE role = ?;
        """;
        Role role = jdbcTemplate.queryForObject(
                query,
                (rs,rowNum) -> {
                    Role newRole = new Role(
                            rs.getInt("id"),
                            rs.getString("role")
                    );
                    return newRole;
                },
                name
        );
        return role;
    }
}
