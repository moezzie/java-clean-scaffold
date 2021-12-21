package se.moeser.javacleanscaffold.application.usecase.user.createuser;

import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserRequestInterface;

public class CreateUserRequest implements CreateUserRequestInterface {
    private String email;
    private String username;
    private String password;

    public CreateUserRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
