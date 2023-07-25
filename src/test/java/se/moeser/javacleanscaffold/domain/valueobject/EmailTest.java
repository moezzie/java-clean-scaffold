package se.moeser.javacleanscaffold.domain.valueobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;

public class EmailTest {

    @Test
    void testEmail() throws InvalidEmailException {
        String expected = "email@email.com";
        Email e = new Email(expected);

        Assertions.assertEquals(expected, e.toString());
    }

    @Test
    void testInvalidEmail() {
        Assertions.assertThrows(InvalidEmailException.class, () -> {
            new Email("invalidemail.com");
        });
    }
}
