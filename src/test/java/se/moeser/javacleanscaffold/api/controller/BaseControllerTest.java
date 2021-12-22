package se.moeser.javacleanscaffold.api.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}
