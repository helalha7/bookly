package com.dev.bookly.scheduling.exceptions;

public class ServiceException extends SchedulingException{
    public ServiceException(String message) {
        super (message , "SERVICE_ERROR");
    }
}
