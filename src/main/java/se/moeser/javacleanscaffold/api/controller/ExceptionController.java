package se.moeser.javacleanscaffold.api.controller;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${debug}")
    boolean debug;

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<Object> handleApiException(ApiException e) {
        ExceptionDto dto = new ExceptionDto(e.getMessage(), e);

        return new ResponseEntity<>(dto, dto.getStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        ExceptionDto dto;

        if (this.debug) {
            // Include full message for debugging
            dto = new ExceptionDto(e.getMessage());
            dto.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            // If in prod, output a generic error message
            dto = new ExceptionDto();
            dto.setMessage("Internal server error");
            dto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(dto, dto.getStatus());
    }
}
