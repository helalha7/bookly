package com.dev.bookly.user.validators;

import com.dev.bookly.user.dtos.requests.UserAccountStatusUpdateRequestDTO;
import com.dev.bookly.user.exceptions.UserInvalidDataException;

public class AccountStatusValidator {
    public static void validate(UserAccountStatusUpdateRequestDTO dto) {
        if(dto.getActive() == null)
            throw new UserInvalidDataException("'active' is null");
    }
}
