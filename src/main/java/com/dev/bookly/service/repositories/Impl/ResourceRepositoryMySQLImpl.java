package com.dev.bookly.service.repositories.Impl;

import com.dev.bookly.global.pagination.PageResult;
import com.dev.bookly.service.domain.Resource;
import com.dev.bookly.service.repositories.ResourceRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository("ResourceRepoMySQL")

public class ResourceRepositoryMySQLImpl implements ResourceRepository {

    private JdbcTemplate jdbcTemplate;

    public ResourceRepositoryMySQLImpl(DataSource dataSource ) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PageResult<Resource> findByServiceId(Long serviceId, int offset, int limit) {
        String query = """
        SELECT *
        FROM resources
        WHERE service_id = ?
        ORDER BY id
        LIMIT ? OFFSET ?
        """;

        List<Resource> resources = jdbcTemplate.query(query, (rs, rowNum) -> new Resource(
                rs.getLong("id"),
                rs.getLong("service_id"),
                rs.getString("name"),
                rs.getInt("capacity")
        ), serviceId, limit, offset);

        String countQuery = "SELECT COUNT(*) FROM resources WHERE service_id = ?";
        Long total = jdbcTemplate.queryForObject(countQuery, Long.class, serviceId);

        return new PageResult<>(resources, total != null ? total : 0L);
    }

    @Override
    public Resource save(Resource resource) {
        String query =
                """
                  insert into resources(service_id , name , capacity) values (? ,? , ?);  
                """;
        jdbcTemplate.update(query,resource.getServiceId(),resource.getName(),resource.getCapacity());
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        resource.setId(id);
        return resource;
    }

    @Override
    public Resource update(Long resourceId, Resource resource) {
        String query =
                """
                   update resources set name = ? , capacity = ? where id = ?;     
                """;
        jdbcTemplate.update(query,resource.getName(),resource.getCapacity(),resourceId);
        resource.setId(resourceId);
        return resource;
    }

    @Override
    public void delete(Long resourceId) {
        String query =
                """
                   delete from resources where id = ?     
                """;
        jdbcTemplate.update(query,resourceId);
    }

    @Override
    public Resource getResourceById(Long resourceId) {
        String query = "SELECT * FROM resources WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{resourceId}, (rs, rowNum) ->
                    new Resource(
                            rs.getLong("id"),
                            rs.getLong("service_id"),
                            rs.getString("name"),
                            rs.getInt("capacity")
                    )
            );
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean existsByName(String name) {
        String query = "SELECT COUNT(*) FROM resources WHERE name = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, name);
        return count != null && count > 0;
    }
}
