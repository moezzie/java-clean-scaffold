package se.moeser.javacleanscaffold.api.dto;

public class AuthenticationResponse {
    private final long id;
    private final String token;

    public AuthenticationResponse(long id, String token) {
        this.id = id;
        this.token = token;
    }

    public long getId() {
        return this.id;
    }

    public String getToken() {
        return this.token;
    }
}
