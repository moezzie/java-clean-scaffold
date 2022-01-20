package se.moeser.javacleanscaffold.application.usecase.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUser;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUserRequest;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUserRequestInterface;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUser;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticateUserTest {
    @Mock
    private UserRepositoryInterface userRepository;

    private AuthenticateUserRequestInterface requestDto;

    @BeforeEach
    void setMockOutput() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        this.requestDto = new AuthenticateUserRequest("user1");

        User user1 =  new User();
        lenient().when(userRepository.getUserByUsername("user1")).thenReturn(user1);
        lenient().when(userRepository.getUserByUsername("no-user")).thenReturn(null);
    }

    @Test
    void TestAuthenticateUser() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        AuthenticateUser usecase = new AuthenticateUser(this.userRepository);
        usecase.authenticateUser(this.requestDto);
    }

    @Test
    void TestUserNotFound() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        this.requestDto = new AuthenticateUserRequest("no-user");

        AuthenticateUser usecase = new AuthenticateUser(this.userRepository);
        usecase.authenticateUser(this.requestDto);
    }
}
