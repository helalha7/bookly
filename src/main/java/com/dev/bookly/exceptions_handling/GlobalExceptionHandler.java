package com.dev.bookly.exceptions_handling;

import com.dev.bookly.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        String message = userNotFoundException.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
