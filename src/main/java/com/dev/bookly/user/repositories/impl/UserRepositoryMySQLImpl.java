package com.dev.bookly.user.repositories.impl;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.user.domains.Account;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository("UserRepoMySQL")
public class UserRepositoryMySQLImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryMySQLImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        String query = """
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

        Map<Long,User> byUserId = jdbcTemplate.query(
                query,
                (rs) -> {
                    Map<Long,User> map = new LinkedHashMap<>();
                    while(rs.next()) {
                        Long userId = rs.getLong("user_id");
                        User user = map.get(userId);
                        if (user == null) {
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
                            user = new User(
                                    userId,
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("city"),
                                    rs.getInt("house_number"),
                                    rs.getString("street"),
                                    account
                            );
                            map.put(userId, user);
                        }
                        Role role = new Role(
                                rs.getInt("role_id"),
                                rs.getString("role")
                        );
                        user.getAccount().getRoles().add(role);
                    }
                    return map;
                }
        );
        return new ArrayList<>(byUserId.values());
    }


    @Override
    public User getUserByUsername(String username){
        String query = """
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
        WHERE a.username = ?
        """;
        User user = jdbcTemplate.query(
                query,
                (rs) -> {
                    User newUser = null;
                    while(rs.next()){
                        if(newUser == null) {
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
                            newUser = new User(
                                    rs.getLong("user_id"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("city"),
                                    rs.getInt("house_number"),
                                    rs.getString("street"),
                                    account
                            );
                        }
                        Role role = new Role(
                                rs.getInt("role_id"),
                                rs.getString("role")
                        );
                        newUser.getAccount().getRoles().add(role);
                    }
                    return newUser;
                },
                username
        );
        return user;
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
        return user;
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
        return account;
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }


    @Override
    public void updateUserInfo(Long userId, UserUpdateRequestDTO userUpdateDTO) {

    }

    @Override
    public void updateUserAccountStatus(Long userId, UserAccountStatusUpdateRequestDTO userAccountStatusUpdateRequestDTO) {

    }

    @Override
    public void triggerResetPassword(Long userId) {

    }
}
