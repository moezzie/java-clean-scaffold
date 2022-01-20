package se.moeser.javacleanscaffold.application.usecase.user.authenticate;

public class AuthenticateUserRequest implements AuthenticateUserRequestInterface {

    private final String username;

    public AuthenticateUserRequest(String username) {
       this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
