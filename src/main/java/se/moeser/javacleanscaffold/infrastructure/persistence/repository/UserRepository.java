package se.moeser.javacleanscaffold.infrastructure.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Username;
import se.moeser.javacleanscaffold.infrastructure.persistence.dao.UserDao;
import se.moeser.javacleanscaffold.infrastructure.persistence.dto.UserDto;

@Repository
public class UserRepository implements UserRepositoryInterface {

    @Autowired
    private UserDao dao;

    @Override
    public long createUser(User user) {
        UserDto dto = new UserDto(user);
        this.dao.save(dto);

        return dto.getId();
    }

}
