package com.dev.bookly.user.services.impl;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.role.repositories.RoleRepository;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.UserMapper;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;
import com.dev.bookly.user.exceptions.UserNotFoundException;
import com.dev.bookly.user.repositories.UserRepository;
import com.dev.bookly.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        List<UserResponseDTO> usersDTO = new ArrayList<>();
        for(User user : users) {
            UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(user);
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
        UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(user);
        return userResponseDTO;
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserCreationRequestDTO userCreationDTO) {

        //mapping dtos to domains
        User user = UserMapper.toUser(userCreationDTO);

        //hashing password
        String encryptedPassword = passwordEncoder.encode(user.getAccount().getPassword());
        user.getAccount().setPassword(encryptedPassword);

        List<Role> roles = user.getAccount().getRoles();

        User newUser = userRepository.createUser(user);
        UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(newUser);
        return userResponseDTO;
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
