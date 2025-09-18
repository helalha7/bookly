package com.dev.bookly.scheduling.exceptions;

public class InvalidShiftTimeException extends RuntimeException {
    public InvalidShiftTimeException(String message) {
        super(message);
    }
}
