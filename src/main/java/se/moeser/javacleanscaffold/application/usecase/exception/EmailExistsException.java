package se.moeser.javacleanscaffold.application.usecase.exception;

public class EmailExistsException extends UseCaseException {
    public EmailExistsException() {
        super("Email already exists");
    }
}
