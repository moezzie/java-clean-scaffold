package se.moeser.javacleanscaffold.application.usecase.exception;

public class UseCaseException extends Exception {
    public UseCaseException(String message) {
        super(message);
    }

    public UseCaseException() {
        super();
    }
}
