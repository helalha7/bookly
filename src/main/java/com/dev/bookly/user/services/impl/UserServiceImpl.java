package com.dev.bookly.user.services.impl;

import com.dev.bookly.global.pagination.PageRequestDTO;
import com.dev.bookly.global.pagination.PageResponseDTO;
import com.dev.bookly.global.pagination.PageResult;
import com.dev.bookly.user.domains.Account;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.UserMapper;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;
import com.dev.bookly.user.exceptions.UserAlreadyExistsException;
import com.dev.bookly.user.exceptions.UserNotFoundException;
import com.dev.bookly.user.repositories.AccountRepository;
import com.dev.bookly.user.repositories.AssignedRolesRepository;
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
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AssignedRolesRepository assignedRolesRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            AssignedRolesRepository assignedRolesRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.assignedRolesRepository = assignedRolesRepository;
    }

    @Override
    public PageResponseDTO<UserResponseDTO> getAllUsers(PageRequestDTO pr) {
        //getting the users page requested
        PageResult<User> page = userRepository.getAllUsers(pr.getOffset(), pr.getSize());

        //transforming into UserResponseDTO list
        List<UserResponseDTO> usersDTO = new ArrayList<>();
        for(User user : page.getRows()) {
            UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(user);
            usersDTO.add(userResponseDTO);
        }

        //returning
        return new PageResponseDTO<>(
                usersDTO,
                pr.getPage(),
                pr.getSize(),
                page.getTotal()
        );
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserCreationRequestDTO userCreationDTO) {
        //checking if the username is used
        User existingUser = userRepository.getUserByUsername(userCreationDTO.getUsername());
        if(existingUser != null)
            throw new UserAlreadyExistsException("username " + userCreationDTO.getUsername().trim() + " already used");


        //mapping into domain
        User user = UserMapper.toUser(userCreationDTO);

        //hashing password
        String encryptedPassword = passwordEncoder.encode(user.getAccount().getPassword());
        user.getAccount().setPassword(encryptedPassword);

        //actual logic
        user = userRepository.createUser(user);
        user.getAccount().setUserId(user.getId());
        Account account = accountRepository.createAccount(user.getAccount());
        user.setAccount(account);
        assignedRolesRepository.assignRoles(account.getId(), account.getRoles());

        //mapping into dto
        UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(user);
        return userResponseDTO;
    }


    @Override
    public UserResponseDTO getUserById(Long userId) {
        //getting the user with the specified user id
        User user =userRepository.getUserById(userId);

        //not found check
        if(user == null) {
            throw new UserNotFoundException("User with id : " + userId + " not found!");
        }

        //mapping into a dto
        UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(user);
        return userResponseDTO;
    }

    @Override
    public void deleteUser(Long userId) {
        //trying to delete the user with the specified user id
        int changedRowsCnt = userRepository.deleteUser(userId);

        //not found check
        if(changedRowsCnt == 0)
            throw new UserNotFoundException("User with id : " + userId + " not found!");
    }

    @Override
    @Transactional
    public void updateUserInfo(Long userId, UserUpdateRequestDTO userUpdateDTO) {
        User user = UserMapper.toUser(userUpdateDTO);
        int usersChangedRowsCnt = userRepository.updateUserInfo(userId, user);
        int accountsChangedRowsCnt = accountRepository.updateAccountInfo(userId, user.getAccount());
        System.out.println(accountsChangedRowsCnt);
        if(accountsChangedRowsCnt == 0 && usersChangedRowsCnt == 0)
            throw new UserNotFoundException("User with id : " + userId + " not found");

    }



    @Override
    public void updateUserAccountStatus(Long userId, UserAccountStatusUpdateRequestDTO userAccountStatusUpdateRequestDTO) {

    }

    @Override
    public void triggerResetPassword(Long userId) {

    }


}
