package com.dev.bookly.scheduling.controllers;

import com.dev.bookly.global.ExceptionResponseDTO;
import com.dev.bookly.scheduling.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

/**
 * Global exception handler for scheduling controllers.
 * Converts exceptions into a consistent ExceptionResponseDTO format.
 */
@RestControllerAdvice
public class ShiftExceptionHandler {

    private ResponseEntity<ExceptionResponseDTO> buildResponse(
            Exception ex,
            String error,
            HttpStatus status,
            HttpServletRequest request
    ) {
        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                ex.getMessage(),
                error,
                request.getMethod(),
                status.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(dto, status);
    }

    /**
     * Handle authorization errors when the user does not own the resource.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return buildResponse(ex, "ACCESS_DENIED", HttpStatus.FORBIDDEN, request);
    }


    /**
     * Handle resource not found errors.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return buildResponse(ex, "RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handle invalid shift time errors (e.g., start >= end).
     */
    @ExceptionHandler(InvalidShiftTimeException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInvalidShift(InvalidShiftTimeException ex, HttpServletRequest request) {
        return buildResponse(ex, "INVALID_SHIFT_TIME", HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handle overlapping shift errors.
     */
    @ExceptionHandler(OverlappingShiftException.class)
    public ResponseEntity<ExceptionResponseDTO> handleOverlap(OverlappingShiftException ex, HttpServletRequest request) {
        return buildResponse(ex, "SHIFT_OVERLAP", HttpStatus.CONFLICT, request);
    }

    /**
     * Handle invalid effective dates errors (e.g., from >= to).
     */
    @ExceptionHandler(InvalidEffectiveDates.class)
    public ResponseEntity<ExceptionResponseDTO> InvalidEffectiveDates(InvalidEffectiveDates ex, HttpServletRequest request) {
        return buildResponse(ex, "INVALID_EFFECTIVE_DATES", HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handle when JSON is bad bean validation failed.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return buildResponse(ex, message, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handle when JSON is bad malformed JSON.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseDTO> handleBadJson(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        return buildResponse(ex, "Malformed JSON request", HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handle when JSON is bad malformed wrong type (e.g. "abc" for dayOfWeek).
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponseDTO> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String message = String.format("Invalid value for parameter '%s': %s",
                ex.getName(), ex.getValue());
        return buildResponse(ex, message, HttpStatus.BAD_REQUEST, request);
    }
}
