package se.moeser.javacleanscaffold.application.usecase.user.createuser;

public class CreateUserResponse implements CreateUserResponseInterface {
    private long id;

    public CreateUserResponse(long id) {
        this.id = id;
    }

    // For JSON de-serialization
    public CreateUserResponse() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
