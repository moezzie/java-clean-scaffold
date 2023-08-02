package se.moeser.javacleanscaffold.shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidPasswordException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Password;
import se.moeser.javacleanscaffold.domain.valueobject.Role;
import se.moeser.javacleanscaffold.domain.valueobject.Username;
import se.moeser.javacleanscaffold.infrastructure.persistence.dto.UserDto;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    Username stringToUsername(String username) throws InvalidUsernameException;
    Email stringToEmail(String email) throws InvalidEmailException;
    Password stringToPassword(String password) throws InvalidPasswordException;
    Role stringToRole(String role);

    User userRepoDtoToUser(UserDto dto);
}
