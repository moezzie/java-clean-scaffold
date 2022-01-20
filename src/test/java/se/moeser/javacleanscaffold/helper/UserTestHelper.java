package se.moeser.javacleanscaffold.helper;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserRequest;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponse;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponseInterface;

public class UserTestHelper {
    public static CreateUserResponse createUser(String host, String email, String username, String password) {

        String ENDPOINT = host + "/user";

        CreateUserRequest dto = new CreateUserRequest(email, username, password);
        HttpEntity body = new HttpEntity(dto);
        RestTemplateBuilder builder = new RestTemplateBuilder().rootUri(host);
        TestRestTemplate restTemplate = new TestRestTemplate(builder);

        ResponseEntity<CreateUserResponse> response = restTemplate.postForEntity(ENDPOINT, body, CreateUserResponse.class);

        return response.getBody();
    }

}
