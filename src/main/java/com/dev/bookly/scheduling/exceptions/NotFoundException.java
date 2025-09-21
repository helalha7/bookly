package com.dev.bookly.scheduling.exceptions;

public class NotFoundException extends SchedulingException {
    public NotFoundException(String message) {
        super(message , "RESOURCE_NOT_FOUND");
    }

}
