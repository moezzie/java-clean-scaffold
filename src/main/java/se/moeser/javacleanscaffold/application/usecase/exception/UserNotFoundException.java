package se.moeser.javacleanscaffold.application.usecase.exception;

public class UserNotFoundException extends UseCaseException {
    public UserNotFoundException() {
        super("User not found");
    }
}
