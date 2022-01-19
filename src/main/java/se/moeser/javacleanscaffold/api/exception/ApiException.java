package se.moeser.javacleanscaffold.api.exception;

import org.springframework.dao.InvalidDataAccessApiUsageException;

public class ApiException extends RuntimeException {
   public ApiException(String message) {
      super(message);
   }

   public ApiException(String message, Throwable e) {
      super(message, e);
   }
}
