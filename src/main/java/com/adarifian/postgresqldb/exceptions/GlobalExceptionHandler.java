package com.adarifian.postgresqldb.exceptions;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", extractUserFriendlyMessage(ex));
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    private String extractUserFriendlyMessage(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();

        if (message != null && message.contains("foreign key constraint")) {
            if (message.contains("authors") && message.contains("books")) {
                return "Cannot delete author. Author has associated books.";
            }
            return "Cannot perform operation due to existing references.";
        }

        return "Cannot perform operation due to data integrity constraints.";
    }
}
