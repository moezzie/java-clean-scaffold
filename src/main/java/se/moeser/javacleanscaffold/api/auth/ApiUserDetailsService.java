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

        try {
            user = this.useCase.authenticateUser(username);
        } catch (Exception e) {
            // Do nothing
        }

        if (user == null) {
            throw new UsernameNotFoundException("Incorrect username or password");
        }

        return new ApiUserPrincipal(user);
    }

    public UserDetails loadUserById(long userId) throws UsernameNotFoundException {

        AuthenticateUserResponseInterface user = null;

        try {
            user = this.useCase.getAuthenticatedUserById(userId);
        } catch (Exception e) {
            // Do nothing
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new ApiUserPrincipal(user);
    }
}
