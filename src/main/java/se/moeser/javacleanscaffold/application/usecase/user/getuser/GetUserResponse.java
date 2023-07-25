package se.moeser.javacleanscaffold.application.usecase.user.getuser;

import se.moeser.javacleanscaffold.domain.entity.User;

public class GetUserResponse implements GetUserResponseInterface {

    private User user;

    public GetUserResponse(User user) {
        this.user = user;
    }

    public long getId() {
        return this.user.getId();
    }

    public String getEmail() {
        return this.user.getEmail().toString();
    }

    public String getUsername() {
        return this.user.getUsername().toString();
    }
}
