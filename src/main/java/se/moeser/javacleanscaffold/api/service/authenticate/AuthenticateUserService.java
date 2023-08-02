package se.moeser.javacleanscaffold.api.service.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import se.moeser.javacleanscaffold.api.auth.ApiUserPrincipal;
import se.moeser.javacleanscaffold.api.auth.JwtTokenUtil;
import se.moeser.javacleanscaffold.api.dto.AuthenticationResponse;

@Service
public class AuthenticateUserService {
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticateUserService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public AuthenticationResponse authenticateUser(String username, String password) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        ApiUserPrincipal userDetails = (ApiUserPrincipal) this.userDetailsService.loadUserByUsername(username);
        String token = this.jwtTokenUtil.generateToken(userDetails);

        return new AuthenticationResponse(userDetails.getId(), token);
    }
}
