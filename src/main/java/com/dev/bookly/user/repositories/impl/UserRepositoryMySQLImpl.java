package com.dev.bookly.user.repositories.impl;

import com.dev.bookly.global.pagination.PageResult;
import com.dev.bookly.role.domains.Role;
import com.dev.bookly.user.domains.Account;
import com.dev.bookly.user.domains.User;
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

/**
 * MySQL implementation of the {@link UserRepository} interface using
 * Spring's {@link JdbcTemplate} for data access.
 * <p>
 * This repository manages CRUD operations for {@link User} entities
 * and their associated {@link Account} and {@link Role} data.
 * <p>
 * The implementation relies on SQL joins to load a complete user
 * with their account and assigned roles in a single query.
 *
 * <h2>Key Features</h2>
 * <ul>
 *   <li>Fetches users along with their accounts and roles using LEFT JOINs.</li>
 *   <li>Maps relational data into domain models with {@code mapUser} helper.</li>
 *   <li>Supports dynamic updates (only non-null fields are updated).</li>
 *   <li>Handles user creation and deletion.</li>
 * </ul>
 *
 * <h2>Design Notes</h2>
 * <ul>
 *   <li>Annotated with {@code @Repository("UserRepoMySQL")} for Spring bean registration.</li>
 *   <li>Uses a reusable {@code BASE_SELECT} SQL fragment to ensure consistent mapping.</li>
 *   <li>Uses {@code LinkedHashMap} during mapping to avoid duplicate users when roles are joined.</li>
 *   <li>Roles are accumulated into the {@code User → Account → Roles} structure.</li>
 * </ul>
 *
 * Example usage:
 * <pre>{@code
 * @Service
 * public class UserService {
 *     private final UserRepository userRepo;
 *
 *     public UserService(@Qualifier("UserRepoMySQL") UserRepository repo) {
 *         this.userRepo = repo;
 *     }
 *
 *     public List<User> findAllUsers() {
 *         return userRepo.getAllUsers();
 *     }
 * }
 * }</pre>
 *
 * @author Helal
 */
@Repository("UserRepoMySQL")
public class UserRepositoryMySQLImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    /** SQL fragment used across queries to fetch users with accounts and roles. */
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
    public UserRepositoryMySQLImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Fetches all users with their accounts and assigned roles.
     *
     * @return list of {@link User} objects
     */
    @Override
    public PageResult<User> getAllUsers(int offset, int limit) {
        String query = """
                SELECT
                          u.id AS user_id, u.first_name, u.last_name, u.city, u.street, u.house_number,
                          a.id AS account_id, a.email, a.username, a.is_active, a.password, a.phone_number,
                          ar.role_id, r.role
                        FROM (
                          SELECT *
                          FROM users
                          ORDER BY id
                          LIMIT ? OFFSET ?
                        ) AS u
                        LEFT JOIN accounts a        ON a.user_id = u.id
                        LEFT JOIN assigned_roles ar ON ar.account_id = a.id
                        LEFT JOIN roles r           ON r.id = ar.role_id
        """;
        Map<Long, User> byUserId = jdbcTemplate.query(
                query,
                (rs) -> {
                    Map<Long, User> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        Long userId = rs.getLong("user_id");
                        User user = map.get(userId);
                        user = mapUser(rs, user);
                        map.put(userId, user);
                    }
                    return map;
                },
                limit,
                offset
        );
        String rowsCount = "SELECT COUNT(*) FROM users";
        Long total = jdbcTemplate.queryForObject(rowsCount, Long.class);
        return new PageResult<>(new ArrayList<>(byUserId.values()), total != null ? total : 0L);
    }

    /**
     * Fetches a user by their username.
     *
     * @param username the account username
     * @return {@link User} object, or {@code null} if not found
     */
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

    /**
     * Fetches a user by their unique ID.
     *
     * @param userId the user ID
     * @return {@link User} object, or {@code null} if not found
     */
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

    /**
     * Creates a new user record in the database.
     *
     * @param user the user domain object (without ID)
     * @return persisted {@link User} with generated ID
     */
    @Override
    public User createUser(User user) {
        String query = """
                INSERT INTO users (first_name, last_name, city, street, house_number)
                VALUES(?,?,?,?,?)
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

    /**
     * Deletes a user by ID.
     *
     * @param userId the user ID
     * @return number of rows affected
     */
    @Override
    public int deleteUser(Long userId) {
        String query = """
            DELETE FROM users
            WHERE id = ?
        """;
        return jdbcTemplate.update(query, userId);
    }

    /**
     * Dynamically updates user information (only non-null fields are updated).
     *
     * @param userId the user ID
     * @param user   object containing updated values
     * @return number of rows affected
     */
    @Override
    public int updateUserInfo(Long userId, User user) {
        StringBuilder query = new StringBuilder("UPDATE users SET");
        List<Object> params = new ArrayList<>();

        if (user.getFirstName() != null) {
            query.append(" first_name = ?, ");
            params.add(user.getFirstName());
        }
        if (user.getLastName() != null) {
            query.append(" last_name = ?, ");
            params.add(user.getLastName());
        }
        if (user.getCity() != null) {
            query.append(" city = ?, ");
            params.add(user.getCity());
        }
        if (user.getStreet() != null) {
            query.append(" street = ?, ");
            params.add(user.getStreet());
        }
        if (user.getHouseNumber() != null) {
            query.append(" house_number = ?, ");
            params.add(user.getHouseNumber());
        }

        if (params.isEmpty()) return 0;

        query.setLength(query.length() - 2); // remove trailing ", "
        query.append(" WHERE id = ?");
        params.add(userId);

        return jdbcTemplate.update(query.toString(), params.toArray());
    }


    /**
     * Helper method that maps a row in the ResultSet to a {@link User} object,
     * including the {@link Account} and assigned {@link Role}.
     * <p>
     * If the user already exists in the map, this method adds the new role
     * to the existing account's role list.
     *
     * @param rs           the result set
     * @param existingUser previously mapped user (can be null)
     * @return fully populated {@link User}
     * @throws SQLException if any column access fails
     */
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

        Role role = new Role(
                rs.getInt("role_id"),
                rs.getString("role")
        );
        existingUser.getAccount().getRoles().add(role);

        return existingUser;
    }
}
