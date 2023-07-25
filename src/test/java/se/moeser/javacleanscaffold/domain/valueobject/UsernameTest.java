package se.moeser.javacleanscaffold.domain.valueobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;

public class UsernameTest {
    @Test
    void testUsername() throws InvalidUsernameException {
        String expected = "user1";
        Username u = new Username(expected);

        Assertions.assertEquals(expected, u.toString());
    }

    @Test
    void testInvalidUsername() {
        Assertions.assertThrows(InvalidUsernameException.class, () -> {
            new Username("x");
        });
    }

    @Test
    void testEquals() throws InvalidUsernameException {
        String strUsername = "user1";
        Username username = new Username(strUsername);

        boolean actual = username.equals(strUsername);
        Assertions.assertTrue(actual);
    }

}
