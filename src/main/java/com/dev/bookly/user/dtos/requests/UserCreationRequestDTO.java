package com.dev.bookly.user.dtos.requests;

import com.dev.bookly.role.domains.Role;

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
    private final List<String> roles;

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
            List<String> roles
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

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getStreet() {
        return street;
    }

    public List<String> getRoles() {
        return roles;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "UserCreationRequestDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", roles=" + roles +
                '}';
    }
}
