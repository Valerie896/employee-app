package com.mastery.java.task.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value = EmployeeAppException.class)
    public ResponseEntity<?> handleAppException(EmployeeAppException exception) {

        log.error("Exception:", exception);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", exception.getResponseMessage());

        return new ResponseEntity<>(responseBody, exception.getStatus());
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<?> handleBindException(BindException exception) {

        log.error("Exception:", exception);

        Map<String, Object> responseBody = new HashMap<>();
        String errorMessage = exception.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        responseBody.put("message", errorMessage);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleUnexpectedException(Exception exception) {

        log.error("Unexpected error: ", exception);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Internal error");

        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
