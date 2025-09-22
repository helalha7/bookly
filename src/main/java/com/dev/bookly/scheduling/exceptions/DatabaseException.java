package com.dev.bookly.scheduling.exceptions;

public class DatabaseException extends SchedulingException{
    public DatabaseException(String message) {
        super(message , "DATABASE_ERROR");
    }
}
