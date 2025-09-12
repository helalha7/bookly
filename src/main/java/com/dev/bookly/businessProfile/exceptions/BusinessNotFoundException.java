package com.dev.bookly.businessProfile.exceptions;


public class BusinessNotFoundException extends RuntimeException {
    public BusinessNotFoundException(String message) {
        super(message);
    }
}
