package com.dev.bookly.user.dtos.requests;

public class UserChangePasswordRequestDTO {

    private final String currentPassword;
    private final String newPassword;

    public UserChangePasswordRequestDTO(
            String currentPassword,
            String newPassword
    ) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
