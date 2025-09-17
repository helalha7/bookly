package com.dev.bookly.auth.dtos.request;

public class LoginRequestDTO {
    private final String username;
    private final String password;

    public LoginRequestDTO(String username, String password) {
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
