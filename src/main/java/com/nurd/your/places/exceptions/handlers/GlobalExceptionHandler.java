package com.nurd.your.places.exceptions.handlers;

import com.nurd.your.places.exceptions.exceptions.ConflictException;
import com.nurd.your.places.exceptions.exceptions.CustomAccessDeniedException;
import com.nurd.your.places.exceptions.exceptions.CustomNotFoundException;
import com.nurd.your.places.utils.dtos.formator.ResponseBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
       StringBuilder errors = new StringBuilder();
       for (FieldError error : e.getBindingResult().getFieldErrors()) {
           errors.append(error.getField()).append(" : ").append(error.getDefaultMessage()).append(", \n");
       }
        return ResponseBuilder.renderError(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseBuilder.renderError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e) {
        return ResponseBuilder.renderError(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseBuilder.renderError(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflictException(ConflictException e) {
        return ResponseBuilder.renderError(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<?> handleCustomNotFoundException(CustomNotFoundException e) {
        return ResponseBuilder.renderError(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<?> handleCustomAccessDeniedException(CustomAccessDeniedException e) {
        return ResponseBuilder.renderError(e.getMessage(), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<?> handleDatabaseError(Exception e) {
        return ResponseBuilder.renderError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
