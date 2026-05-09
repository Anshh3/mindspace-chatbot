package com.mindspace.config;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArg(IllegalArgumentException ex) { return error(HttpStatus.BAD_REQUEST, ex.getMessage()); }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) { return error(HttpStatus.UNAUTHORIZED, "Invalid email or password."); }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) { System.err.println("Error: " + ex.getMessage()); return error(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred."); }
    private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("status", status.value(), "error", message, "timestamp", LocalDateTime.now().toString()));
    }
}
