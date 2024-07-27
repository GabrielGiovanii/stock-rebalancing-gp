package br.com.gabrielgiovani.stock_rebalancing_gp.controllers.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            DataIntegrityViolationException.class
    })
    @ResponseBody
    public Map<String, String> methodArgumentNotValid(Exception e) {
        Map<String, String> errors = new HashMap<>();

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) e;
            errors.put(ex.getName(), "Invalid type for parameter " + ex.getName());
        } else if (e instanceof DataIntegrityViolationException) {
            errors.put("DataIntegrityViolation", "Data integrity violation occurred");
        }

        return errors;
    }

    @ExceptionHandler(value = {
            EntityNotFoundException.class,
            EntityRelationshipNotFoundException.class
    })
    public ResponseEntity<String> entityRelationshipNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getClass().getSimpleName());
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> unauthorizedInteraction(SecurityException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getClass().getSimpleName());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> internalServerError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass().getSimpleName());
    }
}