package se.moeser.javacleanscaffold.api.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

   private HttpStatus status = HttpStatus.BAD_REQUEST;

   public ApiException(String message) {
      super(message);
   }

   public ApiException(String message, Throwable e) {
      super(message, e);
   }

   public ApiException(String message, Throwable e, HttpStatus status) {
      super(message, e);
      this.status = status;
   }

   public ApiException(String message, HttpStatus status) {
      super(message);
      this.status = status;
   }

   public HttpStatus getStatus() {
      return status;
   }

   public void setStatus(HttpStatus status) {
      this.status = status;
   }
}
