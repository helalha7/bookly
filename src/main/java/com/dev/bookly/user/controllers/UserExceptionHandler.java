package com.dev.bookly.user.controllers;

import com.dev.bookly.global.ExceptionResponseDTO;
import com.dev.bookly.user.exceptions.UserAlreadyExistsException;
import com.dev.bookly.user.exceptions.UserInvalidDataException;
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

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleUserInvalidDataException(
            UserInvalidDataException userInvalidDataException,
            HttpServletRequest request
    ) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                userInvalidDataException.getMessage(),
                "USER_INVALID_INPUT",
                request.getMethod(),
                400,
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleUserAlreadyExistsException(
            UserAlreadyExistsException userAlreadyExistsException,
            HttpServletRequest request
    ) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                userAlreadyExistsException.getMessage(),
                "USER_ALREADY_EXISTS",
                request.getMethod(),
                400,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
