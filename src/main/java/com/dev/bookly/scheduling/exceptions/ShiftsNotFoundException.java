package com.dev.bookly.scheduling.exceptions;

public class ShiftsNotFoundException extends SchedulingException {

    public ShiftsNotFoundException(String message) {
        super(message, "SHIFT_NOT_FOUND");
    }
}
