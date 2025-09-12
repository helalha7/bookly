package com.dev.bookly.businessProfile.controllers;

import com.dev.bookly.businessProfile.exceptions.BusinessAlreadyExistsException;
import com.dev.bookly.businessProfile.exceptions.BusinessNotFoundException;
import com.dev.bookly.global.ExceptionResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleBusinessNotFountException(
            BusinessNotFoundException businessNotFoundException,
            HttpServletRequest request
    ) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                businessNotFoundException.getMessage(),
                "BUSINESS_NOT_FOUND",
                request.getMethod(),
                404,
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleBusinessAlreadyExistsException(
            BusinessAlreadyExistsException businessAlreadyExistsException,
            HttpServletRequest request
    ){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                businessAlreadyExistsException.getMessage(),
                "BUSINESS_ALREADY_EXISTS",
                request.getMethod(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.CONFLICT);

    }
}

