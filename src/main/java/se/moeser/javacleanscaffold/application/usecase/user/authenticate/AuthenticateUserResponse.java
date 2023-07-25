package se.moeser.javacleanscaffold.application.usecase.user.authenticate;

import se.moeser.javacleanscaffold.domain.entity.User;

public class AuthenticateUserResponse implements AuthenticateUserResponseInterface {

    private final User user;

    public AuthenticateUserResponse(User user) {
        this.user = user;
    }

    @Override
    public long getId() {
        return this.user.getId();
    }

    @Override
    public String getEmail() {
        var email = this.user.getEmail();
        return email != null ? email.toString() : null;
    }

    @Override
    public String getUsername() {
        var username = this.user.getUsername();
        return username != null ? username.toString() : null;
    }

    @Override
    public String getPassword() {
        var password = this.user.getPassword();
        return password != null ? password.toString() : null;
    }

    @Override
    public String getRole() {
        var role = this.user.getRole();
        return role != null ? role.toString() : null;
    }
}
