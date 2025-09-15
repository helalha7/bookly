package com.dev.bookly.service.exceptions.resourcesExceptions;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message){
        super(message);
    }
}