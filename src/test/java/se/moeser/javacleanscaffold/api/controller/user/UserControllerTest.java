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
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUser;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponse;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponseInterface;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserRequest;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends BaseControllerTest {

    @Mock
    private CreateUser createUserUsecase;

    @BeforeEach
    public void setup() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        CreateUserResponseInterface response = new CreateUserResponse((long) 1);
        when(createUserUsecase.createUser(any())).thenReturn(response);
    }

    @Test
    public void testCreateUser() {

        ResponseEntity<CreateUserResponse> response = this.createUser("user1@email.com", "user1", "Password1!");

        int expectedStatus = 200;
        Assertions.assertEquals(expectedStatus, response.getStatusCodeValue());

        Assertions.assertTrue(response.getBody().getId() > 0);
    }

    @Test
    public void TestGetUser() throws JSONException, IOException, InterruptedException {
        String email = "user2@email.com";
        String username = "user2";
        String password = "Password2!";

        // Create a new user
        ResponseEntity<CreateUserResponse> createUserResponse = this.createUser(email, username, password);
        long userId = createUserResponse.getBody().getId();

        String endpoint = "/user/" + createUserResponse.getBody().getId();

        JSONObject actual = this.getRequest(endpoint);

        JSONObject expected = new JSONObject();
        expected.put("id", userId);
        expected.put("email",email);
        expected.put("username", username);

        JSONAssert.assertEquals(expected, actual,true);
    }

    @Test
    public void TestGetInvalidUser() throws IOException, InterruptedException, JSONException {
        // Request GET /user/{id}
        String endpoint = "/user/999";

        JSONObject actual = this.getRequest(endpoint);

        JSONObject expected = new JSONObject();
        expected.put("message", "User not found");

        JSONAssert.assertEquals(actual, expected, true);
    }

    private ResponseEntity<CreateUserResponse> createUser(String email, String username, String password) {

        String ENDPOINT = this.testUrl() + "/user";

        CreateUserRequest dto = new CreateUserRequest(email, username, password);
        HttpEntity body = new HttpEntity(dto);

        ResponseEntity<CreateUserResponse> response = this.restTemplate().postForEntity(ENDPOINT, body, CreateUserResponse.class);

        return response;
    }
}
