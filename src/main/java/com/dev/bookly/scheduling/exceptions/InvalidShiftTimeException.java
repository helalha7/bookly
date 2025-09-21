package com.dev.bookly.scheduling.exceptions;

public class InvalidShiftTimeException extends SchedulingException {
    public InvalidShiftTimeException(String message) {
        super(message , "INVALID_SHIFT_TIME");
    }
}
