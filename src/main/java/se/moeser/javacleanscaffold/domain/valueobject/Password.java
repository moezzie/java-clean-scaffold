package se.moeser.javacleanscaffold.domain.valueobject;

import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {
    private final String password;

    public Password(String password) throws InvalidPasswordException {
        this.validate(password);
        this.password = password;
    }

    private boolean validate(String password) throws InvalidPasswordException {
        if (password.length() < 8) {
            throw new InvalidPasswordException("To short. Must be at least 8 characters long.");
        }

        // Must have both upper case and lower case letters
        if (password.equals(password.toLowerCase(Locale.ROOT))) {
            throw new InvalidPasswordException("Missing upper case letter");
        }

        if (password.equals(password.toUpperCase(Locale.ROOT))) {
            throw new InvalidPasswordException("Missing lower case letter");
        }

        Pattern numberPattern = Pattern.compile("\\d");
        Matcher matcher = numberPattern.matcher(password);
        if (!matcher.find()) {
            throw new InvalidPasswordException("Missing number");
        }

        Pattern specualCharacterPattern = Pattern.compile("[\\W_]");
        matcher = specualCharacterPattern.matcher(password);
        if (!matcher.find()) {
            throw new InvalidPasswordException("Missing special character");
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
