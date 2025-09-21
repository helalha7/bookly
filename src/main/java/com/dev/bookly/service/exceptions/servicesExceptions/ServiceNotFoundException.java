package com.dev.bookly.service.exceptions.servicesExceptions;

public class ServiceNotFoundException extends RuntimeException{
    public ServiceNotFoundException(String message){
        super(message);
    }
}
