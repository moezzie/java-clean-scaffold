package se.moeser.javacleanscaffold.application.usecase.user.authenticate;

import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;

public class AuthenticateUser {

    private final UserRepositoryInterface repository;

    public AuthenticateUser(UserRepositoryInterface repository) {
        this.repository = repository;
    }

    public AuthenticateUserResponseInterface authenticateUser(AuthenticateUserRequestInterface request) throws InvalidUsernameException, InvalidEmailException, InvalidPasswordException {
        String username = request.getUsername();
        User user = this.repository.getUserByUsernameOrEmail(username);

        // No matching user found
        if (user == null) {
            return null;
        }

        return new AuthenticateUserResponse(user);
    }
}
