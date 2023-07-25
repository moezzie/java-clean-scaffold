package se.moeser.javacleanscaffold.helper;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserRequest;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponse;

import java.io.IOException;


public class UserTestHelper {

    public static JSONObject createUser(String host, String email, String username, String password) throws JSONException, IOException, InterruptedException {
        String endpoint = "/user";

        JSONObject body = new JSONObject();
        body.put("email", email);
        body.put("username", username);
        body.put("password", password);

        return SharedTestHelper.postRequest(host, endpoint, body);
    }

    public static JSONObject authenticateUser(String host, String username, String password) throws JSONException, IOException, InterruptedException {
        String endpoint = "/user/authenticate";

        JSONObject credentials = new JSONObject();
        credentials.put("username", username);
        credentials.put("password", password);

        return SharedTestHelper.postRequest(host, endpoint, credentials);
    }

    public static JSONObject createAndAuthenticateUser(String host, String email, String username, String password) throws JSONException, IOException, InterruptedException {
        JSONObject user = UserTestHelper.createUser(host, email, username, password);

        JSONObject response = UserTestHelper.authenticateUser(host, email, password);
        return response;
    }


}
