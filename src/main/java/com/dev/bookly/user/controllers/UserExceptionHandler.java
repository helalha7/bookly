package com.dev.bookly.user.controllers;

import com.dev.bookly.global.ExceptionResponseDTO;
import com.dev.bookly.user.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleUserNotFoundException(
            UserNotFoundException userNotFoundException,
            HttpServletRequest request
    ) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                userNotFoundException.getMessage(),
                "USER_NOT_FOUND",
                request.getMethod(),
                404,
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }
}
