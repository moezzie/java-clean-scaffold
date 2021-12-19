package se.moeser.javacleanscaffold.domain.repository;

import se.moeser.javacleanscaffold.domain.entity.User;

public interface UserRepositoryInterface {
    public long createUser(User user);
}
