package com.dev.bookly.exceptions_handling;

import java.time.LocalDateTime;

public class ExceptionResponseDTO {
    private final String message;
    private final String error;
    private final int status;
    private final String path;
    private final LocalDateTime timestamp;

    public ExceptionResponseDTO(
            String message,
            String error,
            int status,
            String path,
            LocalDateTime timestamp
    ) {
        this.message = message;
        this.error = error;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
