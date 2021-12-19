package se.moeser.javacleanscaffold.application.usecase.user;

import se.moeser.javacleanscaffold.domain.entity.User;

public interface UserRepositoryInterface {
    public long createUser(User user);
}
