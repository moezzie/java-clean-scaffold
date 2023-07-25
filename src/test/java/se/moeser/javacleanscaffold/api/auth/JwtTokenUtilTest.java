package se.moeser.javacleanscaffold.api.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUserResponse;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Role;
import se.moeser.javacleanscaffold.domain.valueobject.Username;

public class JwtTokenUtilTest {

    private String secretKey = "secret-key-123";

    private JwtTokenUtil util;

    private ApiUserPrincipal apiUserPrincipal;

    @BeforeEach
    public void setup() throws InvalidEmailException, InvalidUsernameException {
        var user = new User();
        user.setId(1L);
        user.setEmail(new Email("user1@email.com"));
        user.setUsername(new Username("user1"));
        user.setRole(new Role(Role.USER));

        this.apiUserPrincipal = new ApiUserPrincipal(new AuthenticateUserResponse(user));

        this.util = new JwtTokenUtil(this.secretKey);
    }

    @Test
    public void createToken() {
        var token = generateToken();
        Assertions.assertNotNull(token);
    }

    @Test
    public void verifyToken() {
        var token = generateToken();
        var isValid = this.util.validateToken(token, apiUserPrincipal);
        Assertions.assertTrue(isValid);
    }

    private String generateToken() {
        return util.generateToken(apiUserPrincipal);
    }
}
