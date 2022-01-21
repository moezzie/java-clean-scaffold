package se.moeser.javacleanscaffold.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUser;
import se.moeser.javacleanscaffold.infrastructure.persistence.repository.UserRepository;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public UserRepositoryInterface userRepositoryConfiguration() {
        return new UserRepository();
    }

    @Bean
    public AuthenticateUser authenticateUserUseCase(UserRepositoryInterface userRepository) {
        return new AuthenticateUser(userRepository);
    }
}
