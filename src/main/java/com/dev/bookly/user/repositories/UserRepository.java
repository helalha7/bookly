package com.dev.bookly.user.repositories;

import com.dev.bookly.global.pagination.PageResult;
import com.dev.bookly.role.domains.Role;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;

import java.util.List;

public interface UserRepository {
    PageResult<User> getAllUsers(int offset, int limit);
    User getUserByUsername(String username);
    User getUserById(Long userId);
    User createUser(User user);
    int deleteUser(Long userId);
    int updateUserInfo(Long userId, User user);
    void updateUserAccountStatus(Long userId, UserAccountStatusUpdateRequestDTO userAccountStatusUpdateRequestDTO);
    void triggerResetPassword(Long userId);
}
