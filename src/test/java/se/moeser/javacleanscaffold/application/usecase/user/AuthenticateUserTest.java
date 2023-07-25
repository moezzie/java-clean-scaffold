package se.moeser.javacleanscaffold.application.usecase.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUser;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUserRequest;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUserRequestInterface;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUserResponseInterface;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUser;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Role;
import se.moeser.javacleanscaffold.domain.valueobject.Username;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticateUserTest {
    @Mock
    private UserRepositoryInterface userRepository;

    private String username = "user1";

    @BeforeEach
    void setMockOutput() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        User user1 =  new User();
        user1.setUsername(new Username(this.username));
        user1.setEmail(new Email("email@localhost.local"));
        user1.setRole(new Role(Role.USER));

        Mockito.lenient().when(userRepository.getUserByUsernameOrEmail(this.username)).thenReturn(user1);
        Mockito.lenient().when(userRepository.getUserByUsernameOrEmail("no-user")).thenReturn(null);
    }

    @Test
    void TestAuthenticateUser() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        AuthenticateUser usecase = new AuthenticateUser(this.userRepository);
        AuthenticateUserResponseInterface user = usecase.authenticateUser(this.username);

        Assertions.assertEquals(this.username, user.getUsername().toString());
        Assertions.assertEquals(Role.USER, user.getRole());
    }

    @Test
    void TestUserNotFound() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        AuthenticateUser usecase = new AuthenticateUser(this.userRepository);
        AuthenticateUserResponseInterface user = usecase.authenticateUser("no-user");

        Assertions.assertNull(user);
    }
}
