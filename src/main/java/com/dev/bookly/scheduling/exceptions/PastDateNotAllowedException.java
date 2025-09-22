package com.dev.bookly.scheduling.exceptions;

public class PastDateNotAllowedException extends SchedulingException {
    public PastDateNotAllowedException(String message) {
        super(message , "DATE_IN_PAST");
    }
}
