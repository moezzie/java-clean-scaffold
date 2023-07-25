package se.moeser.javacleanscaffold.api.dto;

import org.springframework.http.HttpStatus;
import se.moeser.javacleanscaffold.api.exception.ApiException;

public class ExceptionDto {
    private String message;
    private HttpStatus status;

    private ApiException exception;

    public ExceptionDto() {}
    public ExceptionDto(String message) {
        this.message = message;
    }

    public ExceptionDto(String message, ApiException e) {
       this.message = message;
       this.exception = e;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
       this.message = message;
    }

    public HttpStatus getStatus() {
        if (this.exception != null) {
            return this.exception.getStatus();
        }

        return HttpStatus.BAD_REQUEST;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
