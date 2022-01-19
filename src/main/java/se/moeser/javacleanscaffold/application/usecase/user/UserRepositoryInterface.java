package se.moeser.javacleanscaffold.application.usecase.user;

import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.infrastructure.persistence.exception.UserNotFoundException;

import java.util.Optional;

public interface UserRepositoryInterface {
    public long createUser(User user);
    public User getUserById(long id) throws InvalidUsernameException, InvalidEmailException, UserNotFoundException;
}
