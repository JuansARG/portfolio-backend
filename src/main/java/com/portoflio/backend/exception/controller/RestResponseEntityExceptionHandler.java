package com.portoflio.backend.exception.controller;

import com.portoflio.backend.exception.dto.ErrorMessage;
import com.portoflio.backend.exception.model.ArgumentInvalidException;
import com.portoflio.backend.exception.model.UserNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException exception){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(ArgumentInvalidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessage> handleArgumentInvalidException(ArgumentInvalidException exception){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception){
//        Map<String, Object> errors = new HashMap<>();
//        exception.getBindingResult()
//                .getFieldErrors()
//                .forEach(error -> {
//                    errors.put(error.getField(), error.getDefaultMessage());
//                });
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatusCode status,
                                                                  @NotNull WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
