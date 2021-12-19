package se.moeser.javacleanscaffold.domain.usecase.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.moeser.javacleanscaffold.domain.dto.UserDtoInterface;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.repository.UserRepositoryInterface;

import static org.mockito.ArgumentMatchers.any;
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
        when(userDto.getPassword()).thenReturn("Password1!");

        when(userRepository.createUser(any(User.class))).thenReturn((long)1);
    }

    @Test
    void TestCreateUser() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        CreateUser usecase = new CreateUser(userRepository);
        usecase.createUser(this.userDto);
    }
}
