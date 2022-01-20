package se.moeser.javacleanscaffold.api.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUser;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUserRequest;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUserResponseInterface;


@Service
public class ApiUserDetailsService implements UserDetailsService {

    private final AuthenticateUser useCase;

    public ApiUserDetailsService(AuthenticateUser useCase) {
        super();
        this.useCase = useCase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthenticateUserResponseInterface user = null;

        AuthenticateUserRequest request = new AuthenticateUserRequest(username);

        try {
            user = this.useCase.authenticateUser(request);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Unable to log in");
        }

        return new ApiUserPrincipal(user);
    }
}
