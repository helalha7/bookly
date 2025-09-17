package com.dev.bookly.auth.services.impl;

import com.dev.bookly.auth.dtos.request.RegisterRequestDTO;
import com.dev.bookly.auth.dtos.response.RegisterResponseDTO;
import com.dev.bookly.auth.services.AuthService;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.UserMapper;
import com.dev.bookly.user.exceptions.UserAlreadyExistsException;
import com.dev.bookly.user.repositories.AccountRepository;
import com.dev.bookly.user.repositories.AssignedRolesRepository;
import com.dev.bookly.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AssignedRolesRepository assignedRolesRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(
            UserRepository userRepository,
            AccountRepository accountRepository,
            AssignedRolesRepository assignedRolesRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.assignedRolesRepository = assignedRolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        //business logic validation
        User existingUser = userRepository.getUserByUsername(registerRequestDTO.getUsername().trim());
        if(existingUser != null)
            throw new UserAlreadyExistsException("username " + registerRequestDTO.getUsername().trim() + " already used");

        User user = UserMapper.toUser(registerRequestDTO);

        user = userRepository.createUser(user);

        user.getAccount().setUserId(user.getId());
        user.getAccount().setPassword(passwordEncoder.encode(user.getAccount().getPassword()));
        user.setAccount(accountRepository.createAccount(user.getAccount()));
        assignedRolesRepository.assignRoles(user.getAccount().getId(), user.getAccount().getRoles());
        return new RegisterResponseDTO(user.getAccount().getUsername(), user.getAccount().getPassword());
    }
}
