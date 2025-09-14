package com.dev.bookly.service.repositories.Impl;

import com.dev.bookly.service.domain.Resource;
import com.dev.bookly.service.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository("ResourceRepoMySQL")

public class ResourceRepositoryMySQLImpl implements ResourceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;

    public ResourceRepositoryMySQLImpl(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.namedJdbc    = new NamedParameterJdbcTemplate(ds);
    }
    private static final RowMapper<Resource> MAPPER = new RowMapper<Resource>() {
        @Override
        public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {
            Resource r = new Resource();
            r.setId(rs.getLong("id"));
            r.setServiceId(rs.getLong("service_id"));
            r.setName(rs.getString("name"));
            r.setCapacity(rs.getInt("capacity"));
            r.setCreatedAt(rs.getDate("created_at"));
            return r;
        }

    };

    @Override
    public List<Resource> findByService(Long serviceId) {

        return jdbcTemplate.query("SELECT * FROM resources WHERE service_id=?",MAPPER,serviceId);


    }

    @Override
    public Resource findById(Long id) {

        List<Resource> list = jdbcTemplate.query("SELECT * FROM resources WHERE service_id=?",MAPPER,id);
        return list.getFirst();

    }

    @Override
    public boolean existsByName(Long serviceId, String name) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM resources WHERE service_id=? AND LOWER(name)=LOWER(?)",Integer.class,serviceId,name);

        return count!=null && count>0;
    }

    @Override
    public Resource save(Resource resource) {
        String sql =
                """
                  insert into resources(service_id , name , capacity) values (? ,? , ?);
                """;
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, resource.getServiceId());
            ps.setString(2, resource.getName());
            ps.setInt(3, resource.getCapacity());
            return ps;
        }, kh);

        resource.setId(Objects.requireNonNull(kh.getKey()).longValue());
        return resource;
    }

    @Override
    public Resource update(Long resourceId, Resource resource) {
        // Update only non-null values via COALESCE; requires named params
        final String sql = """
            UPDATE resources
               SET name     = COALESCE(:name, name),
                   capacity = COALESCE(:capacity, capacity)
             WHERE id = :id
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", resourceId)
                .addValue("name", resource.getName())
                .addValue("capacity", resource.getCapacity()); // make this Integer in domain to skip when null

        int rows = namedJdbc.update(sql, params);
        if (rows == 0) {
            throw new IllegalStateException("Resource not found: " + resourceId);
        }

        return namedJdbc.queryForObject(
                "SELECT * FROM resources WHERE id = :id",
                new MapSqlParameterSource("id", resourceId),
                MAPPER
        );
    }
}
