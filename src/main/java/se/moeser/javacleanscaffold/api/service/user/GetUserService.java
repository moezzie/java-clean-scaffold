package se.moeser.javacleanscaffold.api.service.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import se.moeser.javacleanscaffold.api.auth.ApiUserPrincipal;
import se.moeser.javacleanscaffold.api.exception.ApiException;
import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.application.usecase.user.getuser.GetUser;
import se.moeser.javacleanscaffold.application.usecase.user.getuser.GetUserResponseInterface;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.infrastructure.persistence.exception.UserNotFoundException;

import java.util.Optional;

@Service
public class GetUserService {

    private final UserRepositoryInterface userRepository;

    public GetUserService(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    public GetUserResponseInterface getUser(long id, ApiUserPrincipal currentUser) throws UserNotFoundException, InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        // Can only access own user
        if (id != currentUser.getId()) {
            throw new ApiException("Forbidden", HttpStatus.FORBIDDEN);
        }

        Optional<GetUserResponseInterface> response;

        GetUser useCase = new GetUser(this.userRepository);
        response = useCase.getUserById(id);

        if (response.isEmpty()) {
            throw new ApiException("User not found");
        }

        return response.get();
    }
}
