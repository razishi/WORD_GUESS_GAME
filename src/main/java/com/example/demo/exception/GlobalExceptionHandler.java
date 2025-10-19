package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Global exception handler for REST API errors.
 * Catches exceptions thrown from any controller and returns standardized HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentExceptions thrown in the application.
     * Typically used when input validation fails.
     *
     * @param ex the thrown IllegalArgumentException
     * @return ResponseEntity with HTTP 400 Bad Request and the exception message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArg(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles any other unhandled exceptions in the application.
     * Logs the error and returns a generic server error response to the client.
     *
     * @param ex the thrown Exception
     * @return ResponseEntity with HTTP 500 Internal Server Error and generic message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOther(Exception ex) {
        ex.printStackTrace(); // log full stack trace in backend
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong. Please try again.");
    }
}
