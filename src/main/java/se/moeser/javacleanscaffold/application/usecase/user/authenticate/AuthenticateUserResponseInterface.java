package se.moeser.javacleanscaffold.application.usecase.user.authenticate;

public interface AuthenticateUserResponseInterface {
    public long getId();
    public String getEmail();
    public String getUsername();
    public String getPassword();
    public String getRole();
}
