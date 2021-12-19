package se.moeser.javacleanscaffold.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.moeser.javacleanscaffold.application.usecase.user.CreateUser;
import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.infrastructure.dto.user.CreateUserDto;

@RestController
public class UserController {
    private static final String template = "Hello %s";

    private final UserRepositoryInterface userRepository;

    @Autowired
    public UserController(UserRepositoryInterface userRepository) {
       this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public long create(@RequestBody CreateUserDto dto) throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        CreateUser usecase = new CreateUser(this.userRepository);
        return usecase.createUser(dto);
    }
}
