package com.dev.bookly.user.repositories.impl;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.user.repositories.AssignedRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository("AssignedRolesRepoMySQL")
public class AssignedRolesMySQLImpl implements AssignedRolesRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AssignedRolesMySQLImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void assignRoles(Long accountId, List<Role> roles) {
        String query = """
            INSERT INTO assigned_roles (account_id, role_id) VALUES(?,?);
        """;
        for(Role role : roles) {
            jdbcTemplate.update(
                    query,
                    accountId,
                    role.getId()
            );
        }
    }
}
