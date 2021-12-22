package se.moeser.javacleanscaffold.api.controller.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends BaseControllerTest {

    @Value("${tests.url.domain}")
    private String domain;

    @Mock
    private CreateUser createUserUsecase;


    @BeforeEach
    public void setup() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        CreateUserResponseInterface response = new CreateUserResponse((long) 1);
        when(createUserUsecase.createUser(any())).thenReturn(response);
    }

    @Test
    public void testCreateUser() {
        String ENDPOINT = this.testUrl() + "/user";

        CreateUserRequest dto = new CreateUserRequest("user1@email.com", "user1", "Password1!");
        HttpEntity body = new HttpEntity(dto);

        ResponseEntity<CreateUserResponse> response = this.restTemplate().postForEntity(ENDPOINT, body, CreateUserResponse.class);

        int expectedStatus = 200;
        Assertions.assertEquals(expectedStatus, response.getStatusCodeValue());

        long expectedId = 1;
        Assertions.assertEquals(expectedId, response.getBody().getId());
    }
}
