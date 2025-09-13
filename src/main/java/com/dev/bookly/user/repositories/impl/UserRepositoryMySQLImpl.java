package com.dev.bookly.user.repositories.impl;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.user.domains.Account;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository("UserRepoMySQL")
public class UserRepositoryMySQLImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    private static final String BASE_SELECT = """
        SELECT u.id AS user_id,
               u.first_name,
               u.last_name,
               u.city,
               u.street,
               u.house_number,
               a.id AS account_id,
               a.email,
               a.phone_number,
               a.username,
               a.password,
               a.is_active,
               ar.role_id,
               r.role
        FROM users AS u
        LEFT JOIN accounts AS a
               ON u.id = a.user_id
        LEFT JOIN assigned_roles AS ar
               ON a.id = ar.account_id
        LEFT JOIN roles AS r
               ON ar.role_id = r.id
        """;

    @Autowired
    public UserRepositoryMySQLImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<User> getAllUsers() {
        Map<Long, User> byUserId = jdbcTemplate.query(
                BASE_SELECT,
                (rs) -> {
                    Map<Long, User> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        Long userId = rs.getLong("user_id");
                        User user = map.get(userId);
                        user = mapUser(rs, user);
                        map.put(userId, user);
                    }
                    return map;
                }
        );
        return new ArrayList<>(byUserId.values());
    }



    @Override
    public User getUserByUsername(String username) {
        return jdbcTemplate.query(
                BASE_SELECT + " WHERE a.username = ?",
                (rs) -> {
                    User user = null;
                    while (rs.next()) {
                        user = mapUser(rs, user);
                    }
                    return user;
                },
                username
        );
    }



    @Override
    public User getUserById(Long userId) {
        return jdbcTemplate.query(
                BASE_SELECT + " WHERE u.id = ?",
                (rs) -> {
                    User user = null;
                    while (rs.next()) {
                        user = mapUser(rs, user);
                    }
                    return user;
                },
                userId
        );
    }



    @Override
    public User createUser(User user) {
        String query = """
                INSERT INTO users (first_name, last_name, city, street, house_number)
                VALUES(?,?,?,?,?);
        """;
        jdbcTemplate.update(
                query,
                user.getFirstName(),
                user.getLastName(),
                user.getCity(),
                user.getStreet(),
                user.getHouseNumber()
        );
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        user.setId(id);
        return user;
    }



    @Override
    public int deleteUser(Long userId) {
        String query = """
            DELETE FROM users
            WHERE id = ?
        """;
        return jdbcTemplate.update(
                query,
                userId
        );
    }


    @Override
    public int updateUserInfo(Long userId, User user) {
        StringBuilder query = new StringBuilder("UPDATE users SET");
        List<Object> params = new ArrayList<>();

        //dynamically creating a query
        if(user.getFirstName() != null) {
            query.append(" first_name = ?, ");
            params.add(user.getFirstName());
        }
        if(user.getLastName() != null) {
            query.append(" last_name = ?, ");
            params.add(user.getLastName());
        }
        if(user.getCity() != null) {
            query.append(" city = ?, ");
            params.add(user.getCity());
        }
        if(user.getStreet() != null) {
            query.append(" street = ?, ");
            params.add(user.getStreet());
        }
        if(user.getHouseNumber() != null) {
            query.append(" house_number = ?, ");
            params.add(user.getHouseNumber());
        }

        //if nothing to update
        if(params.isEmpty())
            return 0;

        //remove ', ' from the end of the query
        query.setLength(query.length()-2);

        //complete the query
        query.append(" WHERE id = ?;");
        params.add(userId);

        return jdbcTemplate.update(
                query.toString(),
                params.toArray()
        );
    }


    @Override
    public void updateUserAccountStatus(Long userId, UserAccountStatusUpdateRequestDTO userAccountStatusUpdateRequestDTO) {

    }

    @Override
    public void triggerResetPassword(Long userId) {

    }

    private User mapUser(ResultSet rs, User existingUser) throws SQLException {
        if (existingUser == null) {
            List<Role> roles = new ArrayList<>();
            Account account = new Account(
                    rs.getLong("account_id"),
                    rs.getLong("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getBoolean("is_active"),
                    roles
            );

            existingUser = new User(
                    rs.getLong("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("city"),
                    rs.getObject("house_number", Integer.class),
                    rs.getString("street"),
                    account
            );
        }

        // Add role (always add, even if user already exists)
        Role role = new Role(
                rs.getInt("role_id"),
                rs.getString("role")
        );
        existingUser.getAccount().getRoles().add(role);

        return existingUser;
    }

}
