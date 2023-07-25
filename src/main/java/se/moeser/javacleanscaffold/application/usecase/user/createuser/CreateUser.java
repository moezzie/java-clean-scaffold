package se.moeser.javacleanscaffold.application.usecase.user.createuser;

import se.moeser.javacleanscaffold.application.usecase.exception.EmailExistsException;
import se.moeser.javacleanscaffold.application.usecase.exception.UseCaseException;
import se.moeser.javacleanscaffold.application.usecase.exception.UsernameExistsException;
import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Password;
import se.moeser.javacleanscaffold.domain.valueobject.Role;
import se.moeser.javacleanscaffold.domain.valueobject.Username;

public class CreateUser {
    private final UserRepositoryInterface repository;

    public CreateUser(UserRepositoryInterface repository) {
       this.repository = repository;
    }

    public CreateUserResponseInterface createUser(CreateUserRequestInterface dto) throws InvalidEmailException, InvalidUsernameException, InvalidPasswordException, UseCaseException {

        Email email = new Email(dto.getEmail());
        Username username = new Username(dto.getUsername());
        Password password = new Password(dto.getPassword());
        Role role = new Role(Role.USER);

        if (usernameExists(dto.getUsername())) {
            throw new UsernameExistsException();
        }

        if (emailExists(dto.getEmail())) {
            throw new EmailExistsException();
        }

        User user = new User(-1, email, username, password, role);

        long userId =  this.repository.createUser(user);

        return new CreateUserResponse(userId);
    }

    private boolean usernameExists(String username) throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
       User user = this.repository.getUserByUsername(username);
       return user != null;
    }

    private boolean emailExists(String email) throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        User user = this.repository.getUserByEmail(email);
        return user != null;
    }

}
