package com.dev.bookly.businessProfile.exceptions;

public class BusinessEmptyException extends RuntimeException {
    public BusinessEmptyException(String message) {
        super(message);
    }
}
