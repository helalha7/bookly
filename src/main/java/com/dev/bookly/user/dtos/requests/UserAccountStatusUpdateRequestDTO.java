package com.dev.bookly.user.dtos.requests;

public class UserAccountStatusUpdateRequestDTO {
    private final Boolean is_active;

    public UserAccountStatusUpdateRequestDTO(
            Boolean is_active
    ) {
        this.is_active = is_active;
    }

    public Boolean is_active() {
        return is_active;
    }
}
