package com.dev.bookly.user.dtos.requests;

public class UserUpdateRequestDTO {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String city;
    private final String street;
    private final int houseNumber;

    public UserUpdateRequestDTO(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String city,
            String street,
            int houseNumber
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getStreet() {
        return street;
    }
}
