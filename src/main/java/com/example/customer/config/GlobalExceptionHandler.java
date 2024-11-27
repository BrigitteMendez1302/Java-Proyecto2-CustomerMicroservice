package com.example.customer.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Global exception handler for managing application-specific exceptions.
 * Provides consistent error responses for various types of exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors for method arguments.
     *
     * @param ex The MethodArgumentNotValidException thrown when validation fails.
     * @return A ResponseEntity containing a map of field-specific error messages with HTTP status 400.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // Collects validation error messages for each invalid field
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField(); // Extracts the field name causing the error
            String errorMessage = error.getDefaultMessage(); // Retrieves the default error message
            errors.put(fieldName, errorMessage); // Adds the field name and error message to the map
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // Returns a 400 Bad Request status
    }

    /**
     * Handles IllegalArgumentException, typically thrown for invalid arguments.
     *
     * @param ex The IllegalArgumentException thrown.
     * @return A ResponseEntity containing the error message and HTTP status 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage()); // Adds the exception message to the response
        errorResponse.put("status", "400"); // Specifies the HTTP status code
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Returns a 400 Bad Request status
    }

    /**
     * Handles DataIntegrityViolationException, typically caused by database constraint violations.
     *
     * @param ex The DataIntegrityViolationException thrown.
     * @return A ResponseEntity containing a detailed error message and HTTP status 409.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Data integrity violation: " + ex.getMessage()); // Adds detailed error message
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT); // Returns a 409 Conflict status
    }

    /**
     * Handles IllegalStateException, typically thrown when an operation is not allowed in the current state.
     *
     * @param ex The IllegalStateException thrown.
     * @return A ResponseEntity containing the error message and HTTP status 400.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage()); // Adds the exception message to the response
        errorResponse.put("status", "400"); // Specifies the HTTP status code
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Returns a 400 Bad Request status
    }

    /**
     * Handles NoSuchElementException, typically thrown when an expected element is not found.
     *
     * @param ex The NoSuchElementException thrown.
     * @return A ResponseEntity containing the error message and HTTP status 404.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage()); // Adds the exception message to the response
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); // Returns a 404 Not Found status
    }
}
