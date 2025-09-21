package com.dev.bookly.service.exceptions.servicesExceptions;

public class InvalidCapacityException extends RuntimeException{
    public InvalidCapacityException(String message){
        super(message);
    }
}