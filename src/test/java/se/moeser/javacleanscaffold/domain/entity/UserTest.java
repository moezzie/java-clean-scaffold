package se.moeser.javacleanscaffold.domain.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Password;
import se.moeser.javacleanscaffold.domain.valueobject.Role;
import se.moeser.javacleanscaffold.domain.valueobject.Username;

public class UserTest {

    @BeforeAll
    static void setup() {

    }

    @BeforeEach
    void init() {

    }

    @Test
    void testUser() {
        User u = new User();

        Assertions.assertInstanceOf(User.class, u);
    }

    @Test
    void testUserFields() throws InvalidUsernameException, InvalidEmailException, InvalidPasswordException {
        long id = 1;
        Email email = new Email("user1@email.com");
        Username username = new Username("user1");
        Password password = new Password("passworD123!");
        Role role = new Role(Role.ADMIN);

        User u = new User(id, email, username, password, role);

        Assertions.assertEquals(id, u.getId());
        Assertions.assertEquals(email, u.getEmail());
        Assertions.assertEquals(username, u.getUsername());
        Assertions.assertEquals(password, u.getPassword());
        Assertions.assertEquals(role, u.getRole());
    }

    @Test
    void testDefaultUserRole() throws InvalidEmailException, InvalidUsernameException, InvalidPasswordException {
        long id = 2;
        Email email = new Email("user2@email.com");
        Username username = new Username("user2");
        Password password = new Password("passworD123!");

        User u = new User(id, email, username, password);

        Assertions.assertEquals(Role.USER, u.getRole().toString());
    }
}
