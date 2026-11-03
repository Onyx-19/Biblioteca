package com.bibliored.demo.config;

import com.bibliored.demo.exceptions.BusinessExceptions;
import com.bibliored.demo.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;

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


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonInvalido(HttpMessageNotReadableException ex) {
        String mensaje = "El cuerpo de la petición es invalido";

        if (ex.getCause() instanceof InvalidFormatException ife && !ife.getPath().isEmpty()) {
            String campo = ife.getPath().get(ife.getPath().size() - 1).getPropertyName();

            if (Number.class.isAssignableFrom(ife.getTargetType())) {
                mensaje = "El campo '" + campo + "' debe ser un número válido";
            } else {
                mensaje = "El campo '" + campo + "' tiene un valor inválido";
            }
        }
        return ResponseEntity.badRequest().body(Map.of("mensaje", mensaje));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("mensaje", mensaje));
    }
}


