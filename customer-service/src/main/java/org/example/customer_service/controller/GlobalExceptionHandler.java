package org.example.customer_service.controller;

import org.example.customer_service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<?> handleException(IdNotFoundException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(DisabledAccountException.class)
    public ResponseEntity<?> handleException(DisabledAccountException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<?> handleException(DuplicateAccountException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> handleException(DuplicateException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(EntityNotFoundException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleException(AccountNotFoundException de) {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST, de.getLocalizedMessage());
        return new ResponseEntity<>(customException, customException.httpStatus());
    }



}
