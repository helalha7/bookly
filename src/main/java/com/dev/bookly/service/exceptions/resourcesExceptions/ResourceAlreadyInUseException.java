package com.dev.bookly.service.exceptions.resourcesExceptions;

public class ResourceAlreadyInUseException extends RuntimeException{
    public ResourceAlreadyInUseException(String message){
        super(message);
    }
}

