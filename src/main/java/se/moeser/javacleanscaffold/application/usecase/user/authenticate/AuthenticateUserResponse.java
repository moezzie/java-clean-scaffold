package se.moeser.javacleanscaffold.application.usecase.user.authenticate;

import se.moeser.javacleanscaffold.domain.entity.User;

public class AuthenticateUserResponse implements AuthenticateUserResponseInterface {

    private User user;

    public AuthenticateUserResponse(User user) {
        this.user = user;
    }

    @Override
    public long getId() {
        return this.user.getId();
    }

    @Override
    public String getEmail() {
        return this.user.getEmail().toString();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername().toString();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword().toString();
    }
}
