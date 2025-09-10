package com.dev.bookly.user.services;

import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserByUsername(String username);
    UserResponseDTO getUserById(Long userId);
    UserResponseDTO createUser(UserCreationRequestDTO userCreationDTO);
    void deleteUser(Long userId);
    void updateUserInfo(Long userId, UserUpdateRequestDTO userUpdateDTO);
    void updateUserAccountStatus(Long userId, UserAccountStatusUpdateRequestDTO userAccountStatusUpdateRequestDTO);
    void triggerResetPassword(Long userId);
}
