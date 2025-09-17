package com.dev.bookly.service.repositories.Impl;

import com.dev.bookly.service.repositories.ServiceRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.dev.bookly.service.domain.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository("ServiceRepoMySQL")
public class ServiceRepositoryMySQLImpl implements ServiceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;

    public ServiceRepositoryMySQLImpl(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.namedJdbc    = new NamedParameterJdbcTemplate(ds);
    }
    private static final RowMapper<Service> MAPPER = (rs, rowNum) -> new Service(
            rs.getLong("id"),
            rs.getLong("business_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getInt("duration_mins"),
            rs.getDouble("price"),
            rs.getBoolean("active"),
            rs.getDate("created_at")
    );

    @Override
    public List<Service> findByBusiness(Long businessId) {
        final String sql = "SELECT * FROM services WHERE business_id = ? ORDER BY id";
        return jdbcTemplate.query(sql, MAPPER, businessId);
    }

    @Override
    public Service findById(Long id) {
        final String sql = "SELECT * FROM services WHERE id = ?";
        List<Service> list = jdbcTemplate.query(sql, MAPPER, id);
        if (list.isEmpty()) {
            throw new IllegalStateException("Service not found: " + id);
        }
        return list.getFirst();
    }

    @Override
    public boolean existsByName(Long businessId, String name) {
        final String sql = "SELECT COUNT(*) FROM services WHERE business_id = ? AND LOWER(name) = LOWER(?)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, businessId, name);
        return count != null && count > 0;
    }

    @Override
    public Service save(Service s) {
        // Insert with generated key (atomic; no SELECT LAST_INSERT_ID())
        final String sql = "INSERT INTO services (business_id, name, description, duration_mins, price, active, created_at) " +
                "VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, s.getBusinessId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getDescription());
            ps.setInt(4, s.getDurationMins());
            ps.setDouble(5, s.getPrice());
            ps.setBoolean(6, s.isActive());
            return ps;
        }, kh);

        Long id = Objects.requireNonNull(kh.getKey()).longValue();
        s.setId(id);

        return findById(id);
    }

    @Override
    public Service update(Service s) {
        final String sql = """
        UPDATE services
           SET name         = COALESCE(:name, name),
               description  = COALESCE(:description, description),
               duration_mins= COALESCE(:duration_mins, duration_mins),
               price        = COALESCE(:price, price),
               active       = COALESCE(:active, active)
         WHERE id = :id
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", s.getId())
                .addValue("name", s.getName())
                .addValue("description", s.getDescription())
                .addValue("duration_mins", s.getDurationMins())
                .addValue("price", s.getPrice())
                .addValue("active", s.isActive());

        int rows = namedJdbc.update(sql, params);
        if (rows == 0) {
            throw new IllegalStateException("Service not found: " + s.getId());
        }

        return findById(s.getId());
    }

}
