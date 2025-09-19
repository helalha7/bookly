package com.dev.bookly.service.exceptions.servicesExceptions;

public class ServiceAlreadyInUseException extends RuntimeException{
    public ServiceAlreadyInUseException(String message){
        super(message);
    }
}

