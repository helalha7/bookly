package com.dev.bookly.scheduling.exceptions;

public class OverlappingShiftException extends RuntimeException {
    public OverlappingShiftException(String message) {
        super(message);
    }
}
