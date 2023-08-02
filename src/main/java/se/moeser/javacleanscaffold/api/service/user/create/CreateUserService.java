package se.moeser.javacleanscaffold.api.service.user.create;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.moeser.javacleanscaffold.api.exception.ApiException;
import se.moeser.javacleanscaffold.application.usecase.exception.UseCaseException;
import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUser;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserRequest;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponseInterface;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;

@Service
public class CreateUserService {

    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationContext applicationContext;

    public CreateUserService(UserRepositoryInterface userRepository, PasswordEncoder passwordEncoder, ApplicationContext applicationContext) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationContext = applicationContext;
    }

    public CreateUserResponseInterface createUser(CreateUserRequest dto) throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        CreateUser usecase = new CreateUser(this.userRepository);

        // Make sure to encrypt the users password
        dto.setPassword(this.passwordEncoder.encode(dto.getPassword()));

        CreateUserResponseInterface response;
        try {
            response = usecase.createUser(dto);
            executeCallback(response);
        } catch (UseCaseException e) {
            throw new ApiException(e.getMessage(), e);
        }

        return response;
    }

    /**
     * If bean which implements CreateUserCallback exists in IoC container
     * execute it.
     * @param user Domain entity User
     */
    private void executeCallback(CreateUserResponseInterface user) {
        CreateUserCallback bean = null;
        try {
            bean = applicationContext.getBean(CreateUserCallback.class);
        } catch (NoSuchBeanDefinitionException e) {
            // No bean of type CreateUserCallback is registered
            return;
        }

        bean.execute(user);
    }
}
