package se.moeser.javacleanscaffold.domain.valueobject;

import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;

public class Username {
    private final String username;

    public Username(String username) throws InvalidUsernameException {
        this.validate(username);
        this.username = username;
    }

    private boolean validate(String username) throws InvalidUsernameException {
        if (username.length() < 3) {
            throw new InvalidUsernameException("Invalid username");
        }

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return this.username.equals(obj.toString());
    }

    @Override
    public String toString() {
        return this.username;
    }
}
