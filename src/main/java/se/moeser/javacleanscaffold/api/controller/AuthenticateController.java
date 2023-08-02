package se.moeser.javacleanscaffold.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.moeser.javacleanscaffold.api.dto.AuthenticationRequest;
import se.moeser.javacleanscaffold.api.dto.AuthenticationResponse;
import se.moeser.javacleanscaffold.api.exception.ApiException;
import se.moeser.javacleanscaffold.api.service.authenticate.AuthenticateUserService;

@RestController
public class AuthenticateController {

    @Autowired
    private final AuthenticateUserService authenticateUserService;

    public AuthenticateController(AuthenticateUserService authenticateUserService) {
        this.authenticateUserService = authenticateUserService;
    }

    @PostMapping("/user/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        AuthenticationResponse response;
        try {
            response = authenticateUserService.authenticateUser(request.getUsername(), request.getPassword());
        } catch (BadCredentialsException e) {
            throw new ApiException("Incorrect username or password", e, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
