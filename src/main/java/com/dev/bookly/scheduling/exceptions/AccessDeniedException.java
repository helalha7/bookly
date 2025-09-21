package com.dev.bookly.scheduling.exceptions;

public class AccessDeniedException extends SchedulingException{
    public AccessDeniedException(String message){
        super(message , "ACCESS_DENIED");
    }
}
