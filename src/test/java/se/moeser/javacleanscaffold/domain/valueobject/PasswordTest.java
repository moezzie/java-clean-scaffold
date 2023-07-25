package se.moeser.javacleanscaffold.domain.valueobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;

public class PasswordTest {

    @Test
    void TestPassword() throws InvalidPasswordException {
        String expected = "Password123!";
        Password p = new Password(expected);
        Assertions.assertEquals(expected, p.toString());
    }

    @Test
    void TestShortPassword(){
        Assertions.assertThrows(InvalidPasswordException.class, () -> {
           new Password("short");
        });
    }

    @Test
    void TestPasswordWithoutUpper() {
        Assertions.assertThrows(InvalidPasswordException.class, () -> {
            new Password("password");
        });
    }

    @Test
    void TestPasswordWithoutLower() {
        Assertions.assertThrows(InvalidPasswordException.class, () -> {
            new Password("PASSWORD");
        });
    }

    @Test
    void TestPasswordWithoutNumber() {
        Assertions.assertThrows(InvalidPasswordException.class, () -> {
            new Password("Password");
        });
    }

    @Test
    void TestPasswordWithoutSpecialCharacter() {
        Assertions.assertThrows(InvalidPasswordException.class, () -> {
            new Password("Password1");
        });
    }
}
