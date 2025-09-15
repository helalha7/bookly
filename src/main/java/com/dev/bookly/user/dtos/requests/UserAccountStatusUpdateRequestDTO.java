package com.dev.bookly.user.dtos.requests;

public class UserAccountStatusUpdateRequestDTO {
    private final Boolean active;

    public UserAccountStatusUpdateRequestDTO(
            Boolean active
    ) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }
}
