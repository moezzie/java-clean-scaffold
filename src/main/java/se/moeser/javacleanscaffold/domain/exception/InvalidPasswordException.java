package se.moeser.javacleanscaffold.domain.exception;

public class InvalidPasswordException extends DomainException {

    public InvalidPasswordException(String message) {
        super(message);
    }
}
