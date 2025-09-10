package com.dev.bookly.user.dtos;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.user.domains.Account;
import com.dev.bookly.user.domains.User;
import com.dev.bookly.user.dtos.requests.UserCreationRequestDTO;
import com.dev.bookly.user.dtos.responses.UserResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User toUser(UserCreationRequestDTO dto) {
        List<Role> roles = new ArrayList<>();
        for(String role : dto.getRoles()) {
            Role r = new Role(0,role);
            roles.add(r);
        }
        Account account = new Account(
                null,
                null,
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                true,
                roles
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

    public static UserResponseDTO toUserResponseDTO(User user) {
        Account account = user.getAccount();
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                account.getUsername(),
                account.getPhoneNumber(),
                account.getEmail(),
                account.getRoles(),
                user.getCity(),
                user.getStreet(),
                user.getHouseNumber()
        );
        return userResponseDTO;
    }
}
