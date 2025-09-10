package com.dev.bookly.user.repositories;

import com.dev.bookly.user.domains.Account;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    User getUserByUsername(String username);
    User getUserById(Long userId);
    User createUser(User user);
    Account createAccount(Account account);
    void deleteUser(Long userId);
    void updateUserInfo(Long userId, UserUpdateRequestDTO userUpdateDTO);
    void updateUserAccountStatus(Long userId, UserAccountStatusUpdateRequestDTO userAccountStatusUpdateRequestDTO);
    void triggerResetPassword(Long userId);
}
