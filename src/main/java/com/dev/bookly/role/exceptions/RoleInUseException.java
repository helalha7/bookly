package com.dev.bookly.role.exceptions;

public class RoleInUseException extends RuntimeException{
    public RoleInUseException(String message) {
        super(message);
    }
}
