package com.dev.bookly.global;

import java.time.LocalDateTime;

public class ExceptionResponseDTO {

    private final String message;
    private final String error;
    private final String httpFunction;
    private final int status;
    private final String path;
    private final LocalDateTime timestamp;

    public ExceptionResponseDTO(String message, String error, String httpFunction, int status, String path, LocalDateTime timestamp) {
        this.message = message;
        this.error = error;
        this.httpFunction = httpFunction;
        this.status = status;
        this.path = path;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getHttpFunction() {
        return httpFunction;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }


}
