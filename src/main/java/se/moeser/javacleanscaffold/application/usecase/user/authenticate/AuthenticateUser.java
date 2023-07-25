package se.moeser.javacleanscaffold.application.usecase.user.authenticate;

import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.infrastructure.persistence.exception.UserNotFoundException;

public class AuthenticateUser {

    private final UserRepositoryInterface repository;

    public AuthenticateUser(UserRepositoryInterface repository) {
        this.repository = repository;
    }

    public AuthenticateUserResponseInterface authenticateUser(String username) throws InvalidUsernameException, InvalidEmailException, InvalidPasswordException {
        User user = this.repository.getUserByUsernameOrEmail(username);

        // No matching user found
        if (user == null) {
            return null;
        }

        return new AuthenticateUserResponse(user);
    }

    public AuthenticateUserResponseInterface getAuthenticatedUserById(long userId) throws InvalidUsernameException, InvalidEmailException, InvalidPasswordException, UserNotFoundException {
        User user = this.repository.getUserById(userId);

        // No matching user found
        if (user == null) {
            return null;
        }

        return new AuthenticateUserResponse(user);
    }
}
