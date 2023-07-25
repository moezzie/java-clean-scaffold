package se.moeser.javacleanscaffold.infrastructure.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Password;
import se.moeser.javacleanscaffold.domain.valueobject.Role;
import se.moeser.javacleanscaffold.domain.valueobject.Username;
import se.moeser.javacleanscaffold.infrastructure.persistence.dao.UserDao;
import se.moeser.javacleanscaffold.infrastructure.persistence.dto.UserDto;
import se.moeser.javacleanscaffold.infrastructure.persistence.exception.UserNotFoundException;

import java.util.Optional;

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

    @Override
    public User getUserById(long id) throws InvalidUsernameException, InvalidEmailException, UserNotFoundException, InvalidPasswordException {
        Optional<UserDto> o = this.dao.findUserDtoById(id);

        if (o.isEmpty()) {
            return null;
        }

        return this.dtoToEntity(o.get());
    }

    @Override
    public User getUserByUsernameOrEmail(String username) throws InvalidUsernameException, InvalidEmailException, InvalidPasswordException {
        Optional<UserDto> o = this.dao.findUserDtoByEmailOrUsername(username, username);

        if (o.isEmpty()) {
            return null;
        }

        return this.dtoToEntity(o.get());
    }

    @Override
    public User getUserByUsername(String username) throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        Optional<UserDto> o = this.dao.findUserDtoByUsername(username);

        if (o.isEmpty()) {
            return null;
        }

        return this.dtoToEntity(o.get());
    }

    @Override
    public User getUserByEmail(String email) throws InvalidPasswordException, InvalidUsernameException, InvalidEmailException {
        Optional<UserDto> o = this.dao.findUserDtoByEmail(email);

        if (o.isEmpty()) {
            return null;
        }

        return this.dtoToEntity(o.get());
    }

    private User dtoToEntity(UserDto dto) throws InvalidUsernameException, InvalidEmailException, InvalidPasswordException {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(new Email(dto.getEmail()));
        user.setUsername(new Username(dto.getUsername()));
        user.setPassword(new Password(dto.getPassword()));
        user.setRole(new Role((dto.getRole())));

        return user;
    }

}
