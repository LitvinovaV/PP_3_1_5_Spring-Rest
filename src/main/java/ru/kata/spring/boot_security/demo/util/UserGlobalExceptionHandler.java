package ru.kata.spring.boot_security.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(NoSuchUserException e) {
        UserIncorrectData incorrectData = new UserIncorrectData();
        incorrectData.setInfo(e.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(Exception e) {
        UserIncorrectData incorrectData = new UserIncorrectData();
        incorrectData.setInfo(e.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }
}
