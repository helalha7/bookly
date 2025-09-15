package com.dev.bookly.activity.repository.impl;

import com.dev.bookly.activity.domain.Activity;
import com.dev.bookly.activity.domain.ActivityAction;
import com.dev.bookly.activity.domain.HttpMethodType;
import com.dev.bookly.activity.dto.request.ActivityRequestDTO;
import com.dev.bookly.activity.repository.ActivityRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {


    private JdbcTemplate jdbcTemplate;
    public ActivityRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Activity log(ActivityRequestDTO newActivity) {

        String query = "INSERT into activity_log (user_id,action,endpoint,http_method,details) VALUES (?,?,?,?,?)";

        KeyHolder kh = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, newActivity.getUserId());
            ps.setString(2, newActivity.getAction().name());
            ps.setString(3, newActivity.getEndpoint());
            ps.setString(4, newActivity.getHttpMethod().name());
            ps.setString(5, newActivity.getDetails());
            return ps;
        }, kh);

        Long id = Objects.requireNonNull(kh.getKey()).longValue();

        return jdbcTemplate.queryForObject(
            "SELECT * FROM activity_log WHERE id = ?",
                (rs,rowNum) -> new Activity(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        ActivityAction.fromString(rs.getString("action")),
                        rs.getString("endpoint"),
                        HttpMethodType.fromString(rs.getString("http_method")),
                        rs.getString("details"),
                        rs.getTimestamp("created_at").toInstant()
                ),
                id
        );


    }

    @Override
    public Activity findById(Long id) {
        String query = "SELECT * FROM activity_log WHERE id = ?";

        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> new Activity(
                rs.getLong("id"),
                rs.getLong("user_id"),
                ActivityAction.fromString(rs.getString("action")),
                rs.getString("endpoint"),
                HttpMethodType.fromString(rs.getString("http_method")),
                rs.getString("details"),
                rs.getTimestamp("created_at").toInstant()
           ),
              id
        );


    }

    @Override
    public List<Activity> findAll() {
        String query = "SELECT * FROM activity_log";
        List<Activity> activities = jdbcTemplate.query(query, (rs, rowNum) -> new Activity(
                rs.getLong("id"),
                rs.getLong("user_id"),
                ActivityAction.fromString(rs.getString("action")),
                rs.getString("endpoint"),
                HttpMethodType.fromString(rs.getString("http_method")),
                rs.getString("details"),
                rs.getTimestamp("created_at").toInstant()
        ));
        return activities;
    }

    @Override
    public List<Activity> findAllByUserId(Long userId) {
        String query = "SELECT * FROM activity_log WHERE user_id = ?";
        List<Activity> activities = jdbcTemplate.query(query, (rs, rowNum) -> new Activity(
                rs.getLong("id"),
                rs.getLong("user_id"),
                ActivityAction.fromString(rs.getString("action")),
                rs.getString("endpoint"),
                HttpMethodType.fromString(rs.getString("http_method")),
                rs.getString("details"),
                rs.getTimestamp("created_at").toInstant()
            ),
                userId
        );
        return activities;
    }
}
