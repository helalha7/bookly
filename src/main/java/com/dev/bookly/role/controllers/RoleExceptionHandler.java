package com.dev.bookly.role.controllers;




import com.dev.bookly.global.ExceptionResponseDTO;
import com.dev.bookly.role.exceptions.RoleAlreadyExistsException;
import com.dev.bookly.role.exceptions.RoleInUseException;
import com.dev.bookly.role.exceptions.RoleInvalidDataException;
import com.dev.bookly.role.exceptions.RoleNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RoleExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleRoleAlreadyExistsException(RoleAlreadyExistsException roleAlreadyExistsException , HttpServletRequest request)
    {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                roleAlreadyExistsException.getMessage(),
                "ROLE_ALREADY_EXISTS",
                request.getMethod(),
                409,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleInvalidDataException(RoleInvalidDataException  roleInvalidDataException , HttpServletRequest request) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                roleInvalidDataException.getMessage(),
                "INVALID_DATA",
                request.getMethod(),
                400,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleRoleNotFoundException(RoleNotFoundException roleNotFoundException ,  HttpServletRequest request) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                roleNotFoundException.getMessage(),
                "USER_NOT_FOUND",
                request.getMethod(),
                404,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleRoleInUseException(RoleInUseException roleInUseException , HttpServletRequest request) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
            roleInUseException.getMessage(),
                "ROLE_IN_USE",
                request.getMethod(),
                409,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.CONFLICT);
    }

}
