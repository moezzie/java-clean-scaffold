package se.moeser.javacleanscaffold.domain.valueobject;

import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;

import java.util.Locale;

public class Password {
    private final String password;

    public Password(String password) throws InvalidPasswordException {
        this.validate(password);
        this.password = password;
    }

    private boolean validate(String password) throws InvalidPasswordException {
        if (password.length() < 8) {
            throw new InvalidPasswordException("Invalid password");
        }

        // Must have borth upper case and lower case letters
        if (password.equals(password.toLowerCase(Locale.ROOT))) {
            throw new InvalidPasswordException("Invalid password");
        }

        if (password.equals(password.toUpperCase(Locale.ROOT))) {
            throw new InvalidPasswordException("Invalid password");
        }

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return this.password.equals(obj.toString());
    }

    @Override
    public String toString() {
        return this.password;
    }
}
