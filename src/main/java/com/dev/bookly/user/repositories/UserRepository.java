package com.dev.bookly.user.repositories;

import com.dev.bookly.global.pagination.PageResult;
import com.dev.bookly.user.domains.User;

public interface UserRepository {
    PageResult<User> getAllUsers(int offset, int limit);
    User getUserByUsername(String username);
    User getUserById(Long userId);
    User createUser(User user);
    int deleteUser(Long userId);
    int updateUserInfo(Long userId, User user);
}
