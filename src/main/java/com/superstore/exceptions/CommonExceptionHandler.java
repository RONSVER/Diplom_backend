package com.superstore.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CommonExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    // Общий обработчик всех исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, WebRequest request) {
        logger.error("Internal server error: {}", exception.getMessage(), exception);
        ErrorResponse errorResponse = createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", request);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Обработчик исключений если ресурс не найден
    @ExceptionHandler({CategoryNotFoundException.class, UserNotFoundException.class, ProductNotFoundException.class, CartNotFoundException.class, CartItemNotFoundException.class, EmptyCartException.class, OrderNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(Exception exception, WebRequest request) {
        logger.error("Resource not found: {}", exception.getMessage(), exception);
        ErrorResponse errorResponse = createErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Обработчик для конфликта email уже существует
    @ExceptionHandler(NoUniqueUserEmailException.class)
    public ResponseEntity<ErrorResponse> handleNoUniqueUserEmailException(Exception exception, WebRequest request) {
        logger.error("Conflict: {}", exception.getMessage(), exception);
        ErrorResponse errorResponse = createErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), request);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // Обработчик для ошибо некорректное имя категории
    @ExceptionHandler(InvalidCategoryNameException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCategoryNameException(Exception exception, WebRequest request) {
        logger.error("Syntax error: {}", exception.getMessage(), exception);
        ErrorResponse errorResponse = createErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage(), request);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Обработчик ошибок валидации
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException exception, WebRequest request) {
        logger.error("Validation error: {}", exception.getMessage(), exception);

        List<ValidationError> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ValidationErrorResponse errorResponse = createErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Обработчик для ошибок фильтрации и сортировки
    @ExceptionHandler(InvalidFilterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFilterException(InvalidFilterException exception, WebRequest request) {
        logger.error("Invalid filter: {}", exception.getMessage(), exception);
        ErrorResponse errorResponse = createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Обработчик IllegalArgumentException и IllegalStateException и InvalidOrderStatusException
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, InvalidOrderStatusException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception exception, WebRequest request) {
        logger.error("Invalid argument: {}", exception.getMessage(), exception);
        ErrorResponse errorResponse = createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createErrorResponse(HttpStatus status, String message, WebRequest request) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                message,
                request.getDescription(false)
        );
    }

    private ValidationErrorResponse createErrorResponse(HttpStatus status, String message, WebRequest request, List<ValidationError> errors) {
        return new ValidationErrorResponse(
                LocalDateTime.now(),
                status.value(),
                message,
                request.getDescription(false),
                errors
        );
    }
}
