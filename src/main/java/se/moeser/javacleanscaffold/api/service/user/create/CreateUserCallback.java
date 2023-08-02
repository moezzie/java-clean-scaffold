package se.moeser.javacleanscaffold.api.service.user.create;

import se.moeser.javacleanscaffold.application.usecase.user.createuser.CreateUserResponseInterface;

public interface CreateUserCallback {
    public void execute(CreateUserResponseInterface user);
}
