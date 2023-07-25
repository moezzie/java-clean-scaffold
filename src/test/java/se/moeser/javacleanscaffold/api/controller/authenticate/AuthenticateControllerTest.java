package se.moeser.javacleanscaffold.api.controller.authenticate;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.moeser.javacleanscaffold.api.auth.JwtTokenUtil;
import se.moeser.javacleanscaffold.api.controller.BaseControllerTest;
import se.moeser.javacleanscaffold.helper.UserTestHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticateControllerTest extends BaseControllerTest {

    @Value("${app.jwt.secret}")
    String jwtSecret;

    @Test
    public void AuthenticateWithUsername() throws JSONException, IOException, InterruptedException {
        String username = "user1";
        String email = username + "@localhost.local";
        String password = "Password1!";

        UserTestHelper.createUser(this.testUrl(), email, username, password);

        JSONObject actual = UserTestHelper.authenticateUser(this.testUrl(), username, password);

        Assertions.assertTrue(actual.has("token"));
        Assertions.assertNotNull(actual.getString("token"));
    }

    @Test
    public void AuthenticateWithEmail() throws JSONException, IOException, InterruptedException {
        String username = "user2";
        String email = username + "@localhost.local";
        String password = "Password1!";
        UserTestHelper.createUser(this.testUrl(), email, username, password);

        JSONObject actual = UserTestHelper.authenticateUser(this.testUrl(), username, password);

        Assertions.assertTrue(actual.has("id"));
        Assertions.assertNotNull(actual.getString("id"));
        Assertions.assertTrue(actual.has("token"));
        Assertions.assertNotNull(actual.getString("token"));
    }

    @Test
    void TestJwtTokenPayload() throws JSONException, IOException, InterruptedException {
        String username = "user3";
        String email = username + "@localhost.local";
        String password = "Password1!";

        JSONObject user = UserTestHelper.createUser(this.testUrl(), email, username, password);

        JSONObject actual = UserTestHelper.authenticateUser(this.testUrl(), username, password);
        String jwtToken = actual.getString("token");

        JwtTokenUtil jwtUtil = new JwtTokenUtil(this.jwtSecret);

        long userId = jwtUtil.extractUserId(jwtToken);

        Assertions.assertEquals(user.getLong("id"), userId);

    }

    private Map<String,String> getUserData(int userNumber) {
        String username = "user" + userNumber;
        String email = username + "@localhost.local";
        String password = "Password1!";

        Map<String, String> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("password", password);

        return userData;
    }
}
