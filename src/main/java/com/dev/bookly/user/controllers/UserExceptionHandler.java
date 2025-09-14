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

/**
 * {@code UserExceptionHandler} is a {@link RestControllerAdvice} that
 * provides centralized exception handling for all user-related controllers.
 * <p>
 * Instead of letting exceptions propagate to the client as raw stack traces,
 * this handler converts them into a consistent error response format
 * defined by {@link ExceptionResponseDTO}.
 *
 * <h2>Handled Exceptions</h2>
 * <ul>
 *   <li>{@link UserNotFoundException} → HTTP 404 NOT FOUND</li>
 *   <li>{@link UserInvalidDataException} → HTTP 400 BAD REQUEST</li>
 *   <li>{@link UserAlreadyExistsException} → HTTP 400 BAD REQUEST</li>
 * </ul>
 *
 * <h2>Error Response Structure</h2>
 * Each handler builds an {@link ExceptionResponseDTO} containing:
 * <ul>
 *   <li>{@code message} – human-readable explanation</li>
 *   <li>{@code error} – machine-readable error code string</li>
 *   <li>{@code method} – HTTP method of the request (GET, POST, etc.)</li>
 *   <li>{@code status} – numeric HTTP status code</li>
 *   <li>{@code path} – request URI</li>
 *   <li>{@code timestamp} – time of error occurrence</li>
 * </ul>
 *
 * Example JSON output:
 * <pre>{@code
 * {
 *   "message": "User with ID 5 not found",
 *   "error": "USER_NOT_FOUND",
 *   "method": "GET",
 *   "status": 404,
 *   "path": "/api/users/5",
 *   "timestamp": "2025-09-14T02:31:45.123"
 * }
 * }</pre>
 *
 * <h2>Design Notes</h2>
 * <ul>
 *   <li>Separating user-related exception handling into this class keeps
 *       global exception handling modular.</li>
 *   <li>Using different {@code error} codes (USER_NOT_FOUND, USER_INVALID_INPUT, etc.)
 *       makes it easier for clients to react programmatically.</li>
 *   <li>Returning {@code ResponseEntity} allows precise control over HTTP status codes.</li>
 * </ul>
 *
 * @author Helal
 * @see ExceptionResponseDTO
 * @see UserNotFoundException
 * @see UserInvalidDataException
 * @see UserAlreadyExistsException
 */
@RestControllerAdvice
public class UserExceptionHandler {

    /**
     * Handles {@link UserNotFoundException}.
     *
     * @param ex      the thrown exception
     * @param request current HTTP request
     * @return {@link ResponseEntity} with 404 status and error payload
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleUserNotFoundException(
            UserNotFoundException ex,
            HttpServletRequest request
    ) {
        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                ex.getMessage(),
                "USER_NOT_FOUND",
                request.getMethod(),
                404,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link UserInvalidDataException}.
     *
     * @param ex      the thrown exception
     * @param request current HTTP request
     * @return {@link ResponseEntity} with 400 status and error payload
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleUserInvalidDataException(
            UserInvalidDataException ex,
            HttpServletRequest request
    ) {
        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                ex.getMessage(),
                "USER_INVALID_INPUT",
                request.getMethod(),
                400,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link UserAlreadyExistsException}.
     *
     * @param ex      the thrown exception
     * @param request current HTTP request
     * @return {@link ResponseEntity} with 400 status and error payload
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        ExceptionResponseDTO dto = new ExceptionResponseDTO(
                ex.getMessage(),
                "USER_ALREADY_EXISTS",
                request.getMethod(),
                400,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
