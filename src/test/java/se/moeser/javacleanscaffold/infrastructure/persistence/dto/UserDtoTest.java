package se.moeser.javacleanscaffold.infrastructure.persistence.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Password;
import se.moeser.javacleanscaffold.domain.valueobject.Username;

public class UserDtoTest {

    private User user;

    @BeforeEach
    public void setup() throws InvalidEmailException, InvalidUsernameException, InvalidPasswordException {
        long id = 1;
        Email email = new Email("user1@email.com");
        Username username = new Username("user1");
        Password password = new Password("Password1!");

        this.user = new User(id, email, username, password);
    }

    @Test
    public void TestUserDtoFromDomainEntity() {
        UserDto dto = new UserDto(this.user);

        Assertions.assertEquals(this.user.getId(), dto.getId());
        Assertions.assertEquals(this.user.getEmail(), dto.getEmail());
        Assertions.assertEquals(this.user.getUsername(), dto.getUsername());
        Assertions.assertEquals(this.user.getPassword(), dto.getPassword());
    }

    @Test
    public void TestUserDtoToDomainEntity() throws InvalidUsernameException, InvalidEmailException {
        UserDto dto = new UserDto(this.user);

        User u = dto.getDomainEntity();

        Assertions.assertEquals(this.user.getId(), u.getId());
        Assertions.assertEquals(this.user.getEmail(), u.getEmail());
        Assertions.assertEquals(this.user.getUsername(), u.getUsername());
    }

    @Test
    public void TestUserDtoToDomainEntityException() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setEmail("invalidemail.com");
        dto.setUsername("user1");
        dto.setPassword("Password1!");

        Assertions.assertThrows(InvalidEmailException.class, () -> {
           dto.getDomainEntity();
        });
    }
}
