package com.dev.bookly.user.services;

import com.dev.bookly.global.pagination.PageRequestDTO;
import com.dev.bookly.global.pagination.PageResponseDTO;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.dtos.requests.UserChangePasswordRequestDTO;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;

public interface UserService {
    PageResponseDTO<UserResponseDTO> getAllUsers(PageRequestDTO pr);
    UserResponseDTO getUserById(Long userId);
    UserResponseDTO createUser(UserCreationRequestDTO userCreationDTO);
    void deleteUser(Long userId);
    void updateUserInfo(Long userId, UserUpdateRequestDTO userUpdateDTO);
    void updateUserAccountStatus(Long userId, UserAccountStatusUpdateRequestDTO userAccountStatusUpdateRequestDTO);
    void updateUserPassword(Long userId, UserChangePasswordRequestDTO userChangePasswordRequestDTO);
    void triggerResetPassword(Long userId);
}
