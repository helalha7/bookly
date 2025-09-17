package com.dev.bookly.service.exceptions.resourcesExceptions;

public class InvalidCapacityException extends RuntimeException{
    public InvalidCapacityException(String message){
        super(message);
    }
}