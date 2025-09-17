package com.dev.bookly.user.validators;

import com.dev.bookly.user.dtos.requests.UserChangePasswordRequestDTO;
import com.dev.bookly.user.exceptions.UserInvalidDataException;

public class PasswordValidator {
    public static void validate(UserChangePasswordRequestDTO dto) {
        if(dto.getCurrentPassword() == null || dto.getCurrentPassword().trim().isEmpty())
            throw new UserInvalidDataException("'currentPassword' is empty!");
        if(dto.getNewPassword() == null || dto.getNewPassword().trim().length() < 8)
            throw new UserInvalidDataException("new password length must be at least 8");
    }
}
