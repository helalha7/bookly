package com.dev.bookly.global;

import com.dev.bookly.global.pagination.PaginationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handlePaginationException(
            PaginationException paginationException,
            HttpServletRequest request
    ) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                paginationException.getMessage(),
                "PAGINATION_ERROR",
                request.getMethod(),
                400,
                request.getRequestURI(),
                LocalDateTime.now()

        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
