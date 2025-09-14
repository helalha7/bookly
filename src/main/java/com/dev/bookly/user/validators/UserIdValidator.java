package com.dev.bookly.user.validators;

import com.dev.bookly.user.exceptions.UserInvalidDataException;

public class UserIdValidator {
    public static void validate(Long userId) {
        if(userId == null || userId < 0)
            throw new UserInvalidDataException("invalid user id !");
    }
}
