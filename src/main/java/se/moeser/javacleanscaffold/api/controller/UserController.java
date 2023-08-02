package se.moeser.javacleanscaffold.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import se.moeser.javacleanscaffold.api.auth.ApiUserPrincipal;
import se.moeser.javacleanscaffold.api.exception.ApiException;
import se.moeser.javacleanscaffold.api.service.user.create.CreateUserService;
import se.moeser.javacleanscaffold.api.service.user.get.GetUserService;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponseInterface;
import se.moeser.javacleanscaffold.application.usecase.user.getuser.GetUserResponseInterface;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserRequest;

@RestController
public class UserController {
    private final CreateUserService createUserService;

    private final GetUserService getUserService;

    @Autowired
    public UserController(CreateUserService createUserService, GetUserService getUserService) {
        this.createUserService = createUserService;
        this.getUserService = getUserService;
    }

    @PostMapping("/user")
    public CreateUserResponseInterface create(@RequestBody CreateUserRequest dto) throws InvalidPasswordException,
            InvalidUsernameException, InvalidEmailException {

        CreateUserResponseInterface response;
        try {
            response = createUserService.createUser(dto);
        } catch (RuntimeException e) {
            throw new ApiException(e.getMessage(), e);
        }

        return response;
    }

    @GetMapping("/user/{id}")
    public GetUserResponseInterface get(@PathVariable long id, @AuthenticationPrincipal ApiUserPrincipal currentUser) {
        GetUserResponseInterface response;
        try {
            response = getUserService.getUser(id, currentUser);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), e);
        }

        return response;
    }
}
