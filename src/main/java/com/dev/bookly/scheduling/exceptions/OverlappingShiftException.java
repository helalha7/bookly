package com.dev.bookly.scheduling.exceptions;

public class OverlappingShiftException extends SchedulingException {
    public OverlappingShiftException(String message) {
        super(message , "SHIFT_OVERLAP");
    }
}
