package se.moeser.javacleanscaffold.api.controller.authenticate;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.moeser.javacleanscaffold.api.controller.BaseControllerTest;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserRequest;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponse;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponseInterface;
import se.moeser.javacleanscaffold.helper.UserTestHelper;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticateControllerTest extends BaseControllerTest {

    @Test
    public void AuthenticateWithUsername() throws JSONException, IOException, InterruptedException {
        String email = "user1@localhost.local";
        String username = "user1";
        String password = "Password1!";

        UserTestHelper.createUser(this.testUrl(), email, username, password);

        JSONObject actual = this.authenticateUser(email, password);

        Assertions.assertTrue(actual.has("token"));
        Assertions.assertNotNull(actual.getString("token"));
    }

    @Test
    public void AuthenticateWithEmail() throws JSONException, IOException, InterruptedException {
        String email = "user2@localhost.local";
        String username = "user2";
        String password = "Password2!";

        UserTestHelper.createUser(this.testUrl(), email, username, password);

        JSONObject actual = this.authenticateUser(email, password);

        Assertions.assertTrue(actual.has("token"));
        Assertions.assertNotNull(actual.getString("token"));
    }

    private JSONObject authenticateUser(String username, String password) throws JSONException, IOException, InterruptedException {
        String endpoint = "/user/authenticate";

        JSONObject body = new JSONObject();
        body.put("username", username);
        body.put("password", password);

        return this.postRequest(endpoint, body);
    }
}
