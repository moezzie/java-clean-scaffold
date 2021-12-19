package se.moeser.javacleanscaffold.domain.exception;

public class InvalidUsernameException extends DomainException {
    public InvalidUsernameException (String message) {
        super(message);
    }
}
