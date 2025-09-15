package com.dev.bookly.activity.controller;

import com.dev.bookly.activity.exceptions.ActivityNotFoundException;
import com.dev.bookly.businessProfile.exceptions.BusinessNotFoundException;
import com.dev.bookly.global.ExceptionResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ActivityHandleExceptions {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleActivityNotFoundException(
            ActivityNotFoundException activityNotFoundException,
            HttpServletRequest request
    ) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                activityNotFoundException.getMessage(),
            "ACTIVITY NOT FOUND",
                request.getMethod(),
                404,
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }
}
