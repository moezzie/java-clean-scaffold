package se.moeser.javacleanscaffold.api.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public abstract class BaseControllerTest {

    @Value("${tests.url.protocol}")
    protected String testProtocol;

    @Value("${tests.url.domain}")
    protected String testDomain;

    @LocalServerPort
    protected int testPort;

    protected String testUrl() {
        return this.testProtocol + "://" + this.testDomain + ":" + this.testPort;
    }

    protected TestRestTemplate restTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder().rootUri(this.testUrl());
        return new TestRestTemplate(builder);
    }

    protected JSONObject postRequest(String endpoint, JSONObject data) throws JSONException, IOException, InterruptedException {
        URI uri = URI.create(this.testUrl() + endpoint);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(data.toString()) )
                .build();

        String d = data.toString();

        return this.jsonRequest(request);
    }

    protected JSONObject jsonRequest(HttpRequest request) throws IOException, InterruptedException, JSONException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = null;

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }
}
