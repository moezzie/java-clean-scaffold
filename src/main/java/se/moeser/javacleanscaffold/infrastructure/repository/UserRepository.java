package se.moeser.javacleanscaffold.infrastructure.repository;

import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.domain.entity.User;

public class UserRepository implements UserRepositoryInterface {
    @Override
    public long createUser(User user) {
        return 0;
    }
}
