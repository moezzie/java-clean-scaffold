package se.moeser.javacleanscaffold.application.usecase.user;

import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Password;
import se.moeser.javacleanscaffold.domain.valueobject.Username;

public class CreateUser {
    private UserRepositoryInterface repository;

    public CreateUser(UserRepositoryInterface repository) {
       this.repository = repository;
    }

    public long createUser(UserDtoInterface dto) throws InvalidEmailException, InvalidUsernameException, InvalidPasswordException {

        Email email = new Email(dto.getEmail());
        Username username = new Username(dto.getUsername());
        Password password = new Password(dto.getPassword());

        User user = new User(-1, email, username, password);

        return this.repository.createUser(user);
    }
}
