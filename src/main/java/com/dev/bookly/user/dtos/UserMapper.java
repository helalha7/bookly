package com.dev.bookly.user.dtos;

import com.dev.bookly.auth.dtos.request.RegisterRequestDTO;
import com.dev.bookly.role.domains.Role;
import com.dev.bookly.role.dtos.RoleDTO;
import com.dev.bookly.role.dtos.RoleMapper;
import com.dev.bookly.user.domains.Account;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.requests.UserUpdateRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User toUser(UserCreationRequestDTO dto) {
        List<Role> roles = new ArrayList<>();
        for(RoleDTO roleDTO : dto.getRoles()) {
            Role role = RoleMapper.toRole(roleDTO);
            roles.add(role);
        }
        Account account = new Account(
                null,
                null,
                dto.getUsername().trim(),
                dto.getPassword().trim(),
                dto.getEmail().trim(),
                dto.getPhoneNumber().trim(),
                true,
                roles
        );
        User user = new User(
                null,
                dto.getFirstName().trim(),
                dto.getLastName().trim(),
                dto.getCity().trim(),
                dto.getHouseNumber(),
                dto.getStreet().trim(),
                account
        );
        return user;
    }

    public static User toUser(UserUpdateRequestDTO dto) {
        Account account = new Account(
                null,
                null,
                null,
                null,
                dto.getEmail(),
                dto.getPhoneNumber(),
                null,
                null
        );

        User user = new User(
                null,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getCity(),
                dto.getHouseNumber(),
                dto.getStreet(),
                account
        );

        return user;
    }

    public static User toUser(RegisterRequestDTO registerRequestDTO) {
        Account account = new Account(
                null,
                null,
                registerRequestDTO.getUsername().trim(),
                registerRequestDTO.getPassword().trim(),
                registerRequestDTO.getEmail(),
                registerRequestDTO.getPhoneNumber(),
                true,
                List.of(new Role(2,"TENANT"))
        );
        User user = new User(
                null,
                registerRequestDTO.getFirstName().trim(),
                registerRequestDTO.getLastName().trim(),
                registerRequestDTO.getCity().trim(),
                registerRequestDTO.getHouseNumber(),
                registerRequestDTO.getStreet().trim(),
                account
        );
        return user;
    }

    public static Account toAccount(UserAccountStatusUpdateRequestDTO dto) {
        Account account = new Account();
        account.setActiveStatus(dto.is_active());
        return account;
    }

    public static UserResponseDTO toUserResponseDTO(User user) {
        Account account = user.getAccount();
        List<RoleDTO> roleDTOs = new ArrayList<>();
        for(Role role : user.getAccount().getRoles()) {
            roleDTOs.add(RoleMapper.toRoleDTO(role));
        }
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                account.getUsername(),
                account.getPhoneNumber(),
                account.getEmail(),
                roleDTOs,
                user.getCity(),
                user.getStreet(),
                user.getHouseNumber()
        );
        return userResponseDTO;
    }

}
