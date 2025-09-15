package com.dev.bookly.global;

import java.time.LocalDateTime;

public class ApiResponseDTO<T> {
    private final boolean success;
    private final T data;
    private final String message;
    private final LocalDateTime timestamp;

    public ApiResponseDTO(boolean success, T data, String message, LocalDateTime timestamp) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
