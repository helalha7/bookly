package com.dev.bookly.service.exceptions.servicesExceptions;

public class ServiceValidationException extends RuntimeException{
    public ServiceValidationException(String message){
        super(message);
    }
}