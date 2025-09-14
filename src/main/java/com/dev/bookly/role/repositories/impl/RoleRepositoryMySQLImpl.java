package com.dev.bookly.role.repositories.impl;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.role.dtos.RoleDTO;
import com.dev.bookly.role.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository("RoleRepoMySQL")
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

    @Override
    public List<Role> getAllRoles() {
        String query =
                """
                    SELECT * FROM roles;
                """;
        List<Role> roles = jdbcTemplate.query(query, new BeanPropertyRowMapper<Role>(Role.class));
        return roles;
    }

    @Override
    public void createRole(Role role) {
        String query =
                """
                  insert into roles(role) values (?);  
                """;
        jdbcTemplate.update(query,role.getRole());
    }

    @Override
    public void deleteRole(Long roleId) {
        String query =
                """
                   delete from roles where id = ?     
                """;
        jdbcTemplate.update(query,roleId);
    }

    @Override
    public void updateRole(Long roleId, Role role) {
        String query =
                """
                   update roles set role = ? where id = ?;     
                """;
        jdbcTemplate.update(query,role.getRole(),roleId);
    }

    public boolean existsByName(String roleName) {
        String query = "SELECT COUNT(*) FROM roles WHERE role = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, roleName);
        return count != null && count > 0;
    }

    public boolean existsById(Long roleId) {
        String query = "SELECT COUNT(*) FROM roles WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, roleId);
        return count != null && count > 0;
    }

    public boolean canDelete(Long roleId) {
        String query = "SELECT COUNT(*) FROM assigned_roles WHERE role_id = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, roleId);
        return count != null && count == 0;
    }
}
