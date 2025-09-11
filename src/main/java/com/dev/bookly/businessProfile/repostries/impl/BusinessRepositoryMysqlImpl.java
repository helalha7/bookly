package com.dev.bookly.businessProfile.repostries.impl;

import com.dev.bookly.businessProfile.domains.Business;
import com.dev.bookly.businessProfile.repostries.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class BusinessRepositoryMysqlImpl  implements BusinessRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BusinessRepositoryMysqlImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Business> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Business save(Business business) {
        return null;
    }
}
