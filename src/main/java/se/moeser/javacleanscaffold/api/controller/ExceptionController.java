package se.moeser.javacleanscaffold.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.moeser.javacleanscaffold.api.dto.ExceptionDto;
import se.moeser.javacleanscaffold.api.exception.ApiException;

/**
 * Exception controller handles all unhandled errors from the API endpoint controller.
 * It gets the exception message and returns its message
 */

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<Object> handleRequestException(ApiException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionDto dto = new ExceptionDto(e.getMessage());

        return new ResponseEntity<>(dto, status);
    }
}
