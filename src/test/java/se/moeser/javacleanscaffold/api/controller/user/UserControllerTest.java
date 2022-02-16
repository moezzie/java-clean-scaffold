package se.moeser.javacleanscaffold.api.controller.user;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.moeser.javacleanscaffold.api.controller.BaseControllerTest;
import se.moeser.javacleanscaffold.application.usecase.exception.UseCaseException;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUser;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponse;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponseInterface;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserRequest;
import se.moeser.javacleanscaffold.helper.SharedTestHelper;
import se.moeser.javacleanscaffold.helper.UserTestHelper;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends BaseControllerTest {

    @Mock
    private CreateUser createUserUsecase;

    @BeforeEach
    public void setup() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException, UseCaseException {
        CreateUserResponseInterface response = new CreateUserResponse((long) 1);
        when(createUserUsecase.createUser(any())).thenReturn(response);
    }

    @Test
    public void testCreateUser() {
        String username = "user33";
        String email = username + "@localhost.local";
        String password = "Password33!";

        ResponseEntity<CreateUserResponse> response = this.createUser(email, username, password);

        int expectedStatus = 200;
        Assertions.assertEquals(expectedStatus, response.getStatusCodeValue());

        Assertions.assertTrue(response.getBody().getId() > 0);
    }

    @Test
    public void TestGetUser() throws JSONException, IOException, InterruptedException {
        String username = "user8";
        String email = username + "@localhost.local";
        String password = "Password8!";

        JSONObject session = UserTestHelper.createAndAuthenticateUser(this.testUrl(), email, username, password);
        long userId = session.getLong("id");


        String endpoint = "/user/" + userId;

        JSONObject expected = new JSONObject();
        expected.put("id", userId);
        expected.put("email", email);
        expected.put("username", username);

        JSONObject actual = SharedTestHelper.authenticatedGetRequest(this.testUrl(), endpoint, session.getString("token"));

        JSONAssert.assertEquals(expected, actual,true);

        // Make sure output does not include the password
        Assertions.assertFalse(actual.has("password"));
    }

    @Test
    public void TestGetInvalidUser() throws IOException, InterruptedException, JSONException {

        String username = "user48";
        String email = username + "@localhost.local";
        String password = "Password3!";

        JSONObject session = UserTestHelper.createAndAuthenticateUser(this.testUrl(), email, username, password);

        // Request GET /user/{id} - 999 User does not exist
        String endpoint = "/user/999";

        JSONObject actual = SharedTestHelper.authenticatedGetRequest(this.testUrl(), endpoint, session.getString("token"));

        JSONObject expected = new JSONObject();
        expected.put("message", "Forbidden");
        expected.put("status", "FORBIDDEN");

        JSONAssert.assertEquals(actual, expected, true);
    }

    @Test
    public void TestUsernamneAlreadyInUse() throws JSONException, IOException, InterruptedException {
        String username = "user27";
        String email = username + "@localhost.local";
        String password = "Password27!";

        UserTestHelper.createUser(this.testUrl(), email, username, password);
        JSONObject actual = UserTestHelper.createUser(this.testUrl(), email, username, password);

        JSONObject expected = new JSONObject();
        expected.put("message","Username already exists");
        expected.put("status","BAD_REQUEST");

        JSONAssert.assertEquals(expected, actual,true);
    }


    @Test
    public void TestEmailAlreadyInUse() throws JSONException, IOException, InterruptedException {
        String username = "user37";
        String email = username + "@localhost.local";
        String password = "Password37!";

        UserTestHelper.createUser(this.testUrl(), email, username, password);
        JSONObject actual = UserTestHelper.createUser(this.testUrl(), email, username + "x", password);

        JSONObject expected = new JSONObject();
        expected.put("message","Email already exists");
        expected.put("status","BAD_REQUEST");

        JSONAssert.assertEquals(expected, actual,true);
    }

    private ResponseEntity<CreateUserResponse> createUser(String email, String username, String password) {

        String ENDPOINT = this.testUrl() + "/user";

        CreateUserRequest dto = new CreateUserRequest(email, username, password);
        HttpEntity body = new HttpEntity(dto);

        ResponseEntity<CreateUserResponse> response = this.restTemplate().postForEntity(ENDPOINT, body, CreateUserResponse.class);

        return response;
    }
}
