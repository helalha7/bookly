package com.dev.bookly.scheduling.exceptions;

public class InvalidEffectiveDates extends SchedulingException{
    public InvalidEffectiveDates(String message) {
        super(message , "INVALID_EFFECTIVE_DATES");
    }
}
