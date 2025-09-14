package com.dev.bookly.auth.dtos.response;

public class RegisterResponseDTO {
    private final String username;
    private final String password;

    public RegisterResponseDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
