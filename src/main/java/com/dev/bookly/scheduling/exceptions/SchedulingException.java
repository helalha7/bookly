package com.dev.bookly.scheduling.exceptions;

public abstract class SchedulingException extends RuntimeException {

    private final String errorCode;

    public SchedulingException(String message , String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}