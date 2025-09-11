package com.dev.bookly.user.dtos.requests;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.role.dtos.RoleDTO;
import com.dev.bookly.user.domains.User;

import java.util.List;

public class UserCreationRequestDTO {

    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final String city;
    private final String street;
    private final int houseNumber;
    private final List<RoleDTO> roles;

    public UserCreationRequestDTO(
            String firstName,
            String lastName,
            String username,
            String password,
            String email,
            String phoneNumber,
            String city,
            String street,
            int houseNumber,
            List<RoleDTO> roles
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public int getHouseNumber() {
        return houseNumber;
    }
}
