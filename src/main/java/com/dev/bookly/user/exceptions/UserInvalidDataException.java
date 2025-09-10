package com.dev.bookly.user.exceptions;

public class UserInvalidDataException extends RuntimeException {
    public UserInvalidDataException(String message) {
        super(message);
    }
}
