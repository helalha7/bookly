package com.dev.bookly.user.dtos.responses;

import com.dev.bookly.role.domains.Role;
import com.dev.bookly.role.dtos.RoleDTO;

import java.util.List;

public class UserResponseDTO {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String phoneNumber;
    private final String email;
    private final List<RoleDTO> roles;
    private final String city;
    private final String street;
    private final Integer houseNumber;

    public UserResponseDTO(
            Long id,
            String firstName,
            String lastName,
            String username,
            String phoneNumber,
            String email,
            List<RoleDTO> roles,
            String city,
            String street,
            Integer houseNumber
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roles = roles;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public String getCity() {
        return city;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public String getStreet() {
        return street;
    }
}
