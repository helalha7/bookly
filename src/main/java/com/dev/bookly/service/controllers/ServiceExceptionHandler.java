package com.dev.bookly.service.controllers;

import com.dev.bookly.global.ExceptionResponseDTO;
import com.dev.bookly.service.exceptions.resourcesExceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleServiceNotFoundException(ResourceNotFoundException resourceNotFoundException , HttpServletRequest request){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                resourceNotFoundException.getMessage(),
                "RESOURCE_NOT_FOUND",
                request.getMethod(),
                404,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleDuplicateServiceException(DuplicateResourceException duplicateResourceException , HttpServletRequest request){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                duplicateResourceException.getMessage(),
                "RESOURCE_ALREADY_EXISTS",
                request.getMethod(),
                409,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleInvalidCapacityException(InvalidCapacityException invalidCapacityException , HttpServletRequest request){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                invalidCapacityException.getMessage(),
                "INVALID_DATA",
                request.getMethod(),
                400,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleServiceAlreadyInUseException(ResourceAlreadyInUseException resourceAlreadyInUseException , HttpServletRequest request){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                resourceAlreadyInUseException.getMessage(),
                "RESOURCE_ALREADY_IN_USE",
                request.getMethod(),
                409,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleServiceValidationException(ValidationException validationException , HttpServletRequest request){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                validationException.getMessage(),
                "INVALID_DATA",
                request.getMethod(),
                400,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }

}