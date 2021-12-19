package se.moeser.javacleanscaffold.api.controller.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.moeser.javacleanscaffold.application.usecase.user.CreateUser;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.infrastructure.dto.user.CreateUserDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String domain = "http://localhost";

    @Mock
    private CreateUser createUserUsecase;

    @BeforeEach
    public void setup() throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        this.restTemplate = new TestRestTemplate();

        when(createUserUsecase.createUser(any())).thenReturn((long)1);
    }

    @Test
    public void testCreateUser() {
        String ENDPOINT = this.domain + ":" + this.port + "/user";

        CreateUserDto dto = new CreateUserDto("user1@email.com", "user1", "Password1!");
        HttpEntity body = new HttpEntity(dto);

        ResponseEntity<Long> response = restTemplate.postForEntity(ENDPOINT, body, Long.class);

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals(response.getBody(), (long) 0);
    }


}
