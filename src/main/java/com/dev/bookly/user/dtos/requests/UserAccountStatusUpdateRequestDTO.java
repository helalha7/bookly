package com.dev.bookly.user.dtos.requests;

public class UserAccountStatusUpdateRequestDTO {
    private final boolean is_active;

    public UserAccountStatusUpdateRequestDTO(
            boolean is_active
    ) {
        this.is_active = is_active;
    }

    public boolean isIs_active() {
        return is_active;
    }
}
