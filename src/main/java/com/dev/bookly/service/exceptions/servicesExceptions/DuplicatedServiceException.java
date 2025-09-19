package com.dev.bookly.service.exceptions.servicesExceptions;

public class DuplicatedServiceException extends RuntimeException{
    public DuplicatedServiceException(String message){
        super(message);
    }
}