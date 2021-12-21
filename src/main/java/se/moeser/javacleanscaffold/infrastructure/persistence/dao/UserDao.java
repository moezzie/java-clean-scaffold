package se.moeser.javacleanscaffold.infrastructure.persistence.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import se.moeser.javacleanscaffold.application.usecase.user.UserRepositoryInterface;
import se.moeser.javacleanscaffold.infrastructure.persistence.dto.UserDto;

public interface UserDao extends JpaRepository<UserDto, Long> {
}
