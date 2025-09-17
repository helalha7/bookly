package com.dev.bookly.service.exceptions.resourcesExceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String message){
        super(message);
    }
}