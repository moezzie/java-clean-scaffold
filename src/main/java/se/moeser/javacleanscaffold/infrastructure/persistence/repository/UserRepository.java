package se.moeser.javacleanscaffold.infrastructure.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.infrastructure.persistence.dao.UserDao;
import se.moeser.javacleanscaffold.infrastructure.persistence.dto.UserDto;
import se.moeser.javacleanscaffold.shared.mapper.UserMapper;

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
    public User getUserById(long id) {
        Optional<UserDto> o = this.dao.findUserDtoById(id);

        if (o.isEmpty()) {
            return null;
        }

        return this.dtoToEntity(o.get());
    }

    @Override
    public User getUserByUsernameOrEmail(String username) {
        Optional<UserDto> o = this.dao.findUserDtoByEmailOrUsername(username, username);

        if (o.isEmpty()) {
            return null;
        }

        return this.dtoToEntity(o.get());
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<UserDto> o = this.dao.findUserDtoByUsername(username);

        if (o.isEmpty()) {
            return null;
        }

        return this.dtoToEntity(o.get());
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<UserDto> o = this.dao.findUserDtoByEmail(email);

        if (o.isEmpty()) {
            return null;
        }

        return this.dtoToEntity(o.get());
    }

    private User dtoToEntity(UserDto dto) {
        return UserMapper.INSTANCE.userRepoDtoToUser(dto);
    }

}
