package se.moeser.javacleanscaffold.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.moeser.javacleanscaffold.api.exception.ApiException;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUser;
import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponseInterface;
import se.moeser.javacleanscaffold.application.usecase.user.getuser.GetUser;
import se.moeser.javacleanscaffold.application.usecase.user.getuser.GetUserResponseInterface;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserRequest;
import se.moeser.javacleanscaffold.infrastructure.persistence.exception.UserNotFoundException;

import java.util.Optional;

@RestController
public class UserController {

    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepositoryInterface userRepository, PasswordEncoder passwordEncoder) {
       this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user")
    public CreateUserResponseInterface create(@RequestBody CreateUserRequest dto) throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        CreateUser usecase = new CreateUser(this.userRepository);

        // Make sure to encrypt the users password
        dto.setPassword(this.passwordEncoder.encode(dto.getPassword()));

        return usecase.createUser(dto);
    }

    @GetMapping("/user/{id}")
    public GetUserResponseInterface get(@PathVariable long id) throws InvalidUsernameException, InvalidEmailException, UserNotFoundException {

        Optional<GetUserResponseInterface> response;

        try {
            GetUser useCase = new GetUser(this.userRepository);
            response = useCase.getUserById(id);
        } catch (Throwable t) {
            throw new ApiException("Unexpected error", t);
        }

        if (response.isEmpty()) {
            throw new ApiException("User not found");
        }

        return response.get();
    }
}
