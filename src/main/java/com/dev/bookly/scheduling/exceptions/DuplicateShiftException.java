package com.dev.bookly.scheduling.exceptions;

public class DuplicateShiftException extends SchedulingException {
    public DuplicateShiftException(String message) {
        super(message , "DUPLICATE_SHIFT");
    }
}
