package se.moeser.javacleanscaffold.domain.valueobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;

import javax.management.InvalidAttributeValueException;

public class PasswordTest {

    @Test
    void TestPassword() throws InvalidPasswordException {
        String expected = "Password123!";
        Password p = new Password(expected);
        Assertions.assertEquals(expected, p.toString());
    }
}
