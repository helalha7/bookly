package com.dev.bookly.user.services.impl;

import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;
import com.dev.bookly.user.exceptions.UserNotFoundException;
import com.dev.bookly.user.repositories.UserRepository;
import com.dev.bookly.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        List<UserResponseDTO> usersDTO = new ArrayList<>();
        for(User user : users) {
            UserResponseDTO userResponseDTO = new UserResponseDTO(user);
            usersDTO.add(userResponseDTO);
        }
        return usersDTO;
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        if(user == null) {
            throw new UserNotFoundException("User with username " + username + " not found!");
        }
        UserResponseDTO userResponseDTO = new UserResponseDTO(user);
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO createUser(UserCreationRequestDTO userCreationDTO) {
        return null;
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
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
