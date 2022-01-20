package se.moeser.javacleanscaffold.application.usecase.user.createuser;

import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Password;
import se.moeser.javacleanscaffold.domain.valueobject.Username;

public class CreateUser {
    private final UserRepositoryInterface repository;

    public CreateUser(UserRepositoryInterface repository) {
       this.repository = repository;
    }

    public CreateUserResponseInterface createUser(CreateUserRequestInterface dto) throws InvalidEmailException, InvalidUsernameException, InvalidPasswordException {

        Email email = new Email(dto.getEmail());
        Username username = new Username(dto.getUsername());
        Password password = new Password(dto.getPassword());

        User user = new User(-1, email, username, password);

        long userId =  this.repository.createUser(user);

        return new CreateUserResponse(userId);
    }
}
