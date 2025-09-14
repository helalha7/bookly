package com.dev.bookly.auth.dtos.request;

import com.dev.bookly.role.dtos.RoleDTO;

import java.util.List;

public class RegisterRequestDTO {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final String city;
    private final String street;
    private final Integer houseNumber;

    public RegisterRequestDTO(
            String firstName,
            String lastName,
            String username,
            String password,
            String email,
            String phoneNumber,
            String city,
            String street,
            Integer houseNumber
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
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public Integer getHouseNumber() {
        return houseNumber;
    }
}
