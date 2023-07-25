package se.moeser.javacleanscaffold.application.usecase.user.getuser;

import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.infrastructure.persistence.exception.UserNotFoundException;

import java.util.Optional;

public class GetUser {

    private UserRepositoryInterface repository;

    public GetUser(UserRepositoryInterface repository) {

        this.repository = repository;
    }

    public Optional<GetUserResponseInterface> getUserById(long userId) throws InvalidUsernameException, InvalidEmailException, UserNotFoundException, InvalidPasswordException {
        User user = this.repository.getUserById(userId);

        Optional<GetUserResponseInterface> o = Optional.ofNullable(null);
        if (user == null) {
            return o;
        }

        return Optional.of(new GetUserResponse(user));
    }
}
