package com.dev.bookly.user.repositories.impl;

import com.dev.bookly.user.domains.Account;
import com.dev.bookly.user.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository("AccountRepoMySQL")
public class AccountRepositoryMySQLImpl implements AccountRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountRepositoryMySQLImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Account createAccount(Account account) {
        String query = """
                INSERT INTO accounts (user_id, email, phone_number, username, password, is_active)
                VALUES (?,?,?,?,?,?)
        """;
        jdbcTemplate.update(
                query,
                account.getUserId(),
                account.getEmail(),
                account.getPhoneNumber(),
                account.getUsername(),
                account.getPassword(),
                account.getActiveStatus()
        );

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        account.setId(id);
        return account;
    }

    @Override
    public int updateAccountInfo(Long userId, Account account) {
        StringBuilder query = new StringBuilder("UPDATE accounts SET");
        List<Object> params = new ArrayList<>();

        if(account.getEmail() != null) {
            query.append(" email = ?, ");
            params.add(account.getEmail());
        }
        if(account.getPhoneNumber() != null) {
            query.append(" phone_number = ?, ");
            params.add(account.getPhoneNumber());
        }
        if(account.getPassword() != null) {
            query.append(" password = ?, ");
            params.add(account.getPassword());
        }
        if(account.getActiveStatus() != null) {
            query.append(" is_active = ?, ");
            params.add(account.getActiveStatus());
        }

        //if nothing to update
        if(params.isEmpty())
            return 0;

        //remove ", " from the end
        query.setLength(query.length()-2);

        //completing query
        query.append(" WHERE user_id = ?;");
        params.add(userId);

        return jdbcTemplate.update(
                query.toString(),
                params.toArray()
        );
    }
}
