package com.superstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({CategoryNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity handleNotFound(Exception exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(NoUniqueUserEmailException.class)
    public ResponseEntity handleNoUniqueUserEmailException(Exception exception, WebRequest request) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
