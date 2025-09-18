package com.dev.bookly.auth.dtos.response;

public class LoginResponseDTO {
    private final String jwtToken;

    public LoginResponseDTO(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
