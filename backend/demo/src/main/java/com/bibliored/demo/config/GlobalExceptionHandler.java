package com.bibliored.demo.config;

import com.bibliored.demo.exceptions.BusinessExceptions;
import com.bibliored.demo.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessExceptions.class)
    public ResponseEntity<Map<String, String>> handleBusiness(BusinessExceptions e){
        return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBusiness(ResourceNotFoundException e){
        return ResponseEntity.status(404).body(Map.of("mensaje", e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().body(
                Map.of("mensaje", "No se puede eliminar el objeto porque tiene movimientos asociados.")
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(
                Map.of("mensaje", e.getMessage())
        );
    }
}


