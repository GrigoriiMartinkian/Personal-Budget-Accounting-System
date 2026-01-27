package com.example.financeproject.exception;

import com.example.financeproject.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 404);
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Пример обработки кастомного исключения
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err ->
                        errors.put(err.getField(), err.getDefaultMessage())
                );

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "error", "VALIDATION_ERROR",
                        "details", errors,
                        "path", request.getRequestURI()
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleConflict(DataIntegrityViolationException e) {
        String message = e.getMostSpecificCause().getMessage();
        String details = "Data already exist";


        if (message.contains("username")) {
            details = "name is taken";
        } else if (message.contains("email")) {
            details = "email is taken";
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", details));
    }


    //  Валидация параметров запроса
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex) {
        String details = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", details);
    }

    //  Ошибки чтения JSON (плохой формат тела запроса)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleJsonError(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "MALFORMED_JSON", "Invalid JSON format or data type");
    }

    // Ошибка типа аргумента
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String details = String.format("Parameter '%s' should be of type %s",
                ex.getName(), ex.getRequiredType().getSimpleName());
        return buildResponse(HttpStatus.BAD_REQUEST, "TYPE_MISMATCH", details);
    }

    // Ошибка метода
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", ex.getMessage());
    }

    //  финальный обработчик
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAll(Exception e) {
        log.error("Unexpected error occurred: ", e);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "An unexpected error occurred");
    }

    // Вспомогательный метод для унификации ответов
    private ResponseEntity<ErrorResponseDto> buildResponse(HttpStatus status, String error, String message) {
        return ResponseEntity.status(status).body(new ErrorResponseDto(
                status.value(),
                error,
                message
        ));
    }

}

