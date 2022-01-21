package se.moeser.javacleanscaffold.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.moeser.javacleanscaffold.api.auth.ApiUserPrincipal;
import se.moeser.javacleanscaffold.api.auth.JwtTokenUtil;
import se.moeser.javacleanscaffold.api.dto.AuthenticationRequest;
import se.moeser.javacleanscaffold.api.dto.AuthenticationResponse;
import se.moeser.javacleanscaffold.api.exception.ApiException;

@RestController
public class AuthenticateController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/user/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ApiException("Incorrect username or password", e, HttpStatus.UNAUTHORIZED);
        }

        ApiUserPrincipal userDetails = (ApiUserPrincipal) this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenUtil.generateToken(userDetails);


        AuthenticationResponse response = new AuthenticationResponse(userDetails.getId(), token);

        return ResponseEntity.ok(response);
    }
}
