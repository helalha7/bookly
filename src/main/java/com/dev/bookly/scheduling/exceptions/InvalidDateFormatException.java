package com.dev.bookly.scheduling.exceptions;

public class InvalidDateFormatException extends SchedulingException{
    public InvalidDateFormatException(String message) {
        super(message , "INVALID_DATE_FORMAT");
    }
}
