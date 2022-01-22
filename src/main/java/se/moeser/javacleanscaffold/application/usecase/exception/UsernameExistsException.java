package se.moeser.javacleanscaffold.application.usecase.exception;

public class UsernameExistsException extends UseCaseException {
    public UsernameExistsException() {
        super("Username already exists");
    }
}
