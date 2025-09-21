package com.dev.bookly.booking.repositories.Impl;

import com.dev.bookly.booking.domains.BookingPolicy;
import com.dev.bookly.booking.repositories.BookingPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository("BookingPolicyRepoMySQL")
public class BookingPolicyRepositoryMySQLImpl implements BookingPolicyRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public BookingPolicyRepositoryMySQLImpl(DataSource ds) {
        this.jdbc = new JdbcTemplate(ds);
    }

    private static final RowMapper<BookingPolicy> MAPPER = new RowMapper<>() {
        @Override public BookingPolicy mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new BookingPolicy(
                    rs.getLong("id"),
                    rs.getLong("service_id"),
                    rs.getInt("max_advance_days"),
                    rs.getInt("cancel_window_hours"),
                    rs.getBoolean("reschedule_allowed"),
                    rs.getTimestamp("created_at").toInstant()
            );
        }
    };

    @Override
    public Optional<BookingPolicy> findByServiceId(Long serviceId) {
        List<BookingPolicy> list = jdbc.query("SELECT * FROM booking_policies WHERE service_id = ?", MAPPER, serviceId);
        return list.stream().findFirst();
    }
}

