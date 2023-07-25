package se.moeser.javacleanscaffold.domain.valueobject;

import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;

public class Email {
    private final String email;

    public Email(String email) throws InvalidEmailException {
        this.validate(email);
        this.email = email;
    }

    private boolean validate(String email) throws InvalidEmailException {
        if (!email.contains("@")) {
            throw new InvalidEmailException("Invalid email");
        }

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return this.email.equals(obj.toString());
    }

    @Override
    public String toString() {
        return this.email;
    }

}