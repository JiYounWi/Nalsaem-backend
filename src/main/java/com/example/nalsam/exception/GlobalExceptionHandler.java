package com.example.nalsam.exception;

import com.example.nalsam.user.exception.PasswordNotCorrectException;
import com.example.nalsam.user.exception.UserAlreadyExistException;
import com.example.nalsam.user.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final int BAD_REQUEST_ERROR = 400;

    private static final int NOT_FOUND_ERROR = 404;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(NOT_FOUND_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(PasswordNotCorrectException.class)
    public ResponseEntity<?> handlePasswordNotCorrectException(PasswordNotCorrectException e){
        return ResponseEntity.status(BAD_REQUEST_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExisstException(UserAlreadyExistException e){
        return ResponseEntity.status(BAD_REQUEST_ERROR).body(e.getMessage());
    }

}

