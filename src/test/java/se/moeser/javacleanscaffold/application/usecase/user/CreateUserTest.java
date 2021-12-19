package se.moeser.javacleanscaffold.application.usecase.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserTest {

    @Mock
    private UserDtoInterface userDto;

    @Mock
    private UserRepositoryInterface userRepository;

    @BeforeEach
    void setMockOutput() {
        when(userDto.getEmail()).thenReturn("user1@email.com");
        when(userDto.getUsername()).thenReturn("user1");
        lenient().when(userDto.getPassword()).thenReturn("Password1!");

        lenient().when(userRepository.createUser(any(User.class))).thenReturn((long) 1);
    }

    @Test
    void TestCreateUser() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        CreateUser usecase = new CreateUser(this.userRepository);
        usecase.createUser(this.userDto);
    }

    @Test
    void TestInvalidPassword() {
        lenient().when(userDto.getPassword()).thenReturn("invalid");

        Assertions.assertThrows(InvalidPasswordException.class, () -> {
            CreateUser usecase = new CreateUser(this.userRepository);
            usecase.createUser(this.userDto);
        });
    }
}
