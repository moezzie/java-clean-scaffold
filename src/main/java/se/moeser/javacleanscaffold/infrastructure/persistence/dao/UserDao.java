package se.moeser.javacleanscaffold.infrastructure.persistence.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import se.moeser.javacleanscaffold.infrastructure.persistence.dto.UserDto;

import java.util.Optional;

public interface UserDao extends JpaRepository<UserDto, Long> {
    public Optional<UserDto> findUserDtoById(Long id);
    public Optional<UserDto> findUserDtoByEmailOrUsername(String email, String username);
    public Optional<UserDto> findUserDtoByUsername(String username);
    public Optional<UserDto> findUserDtoByEmail(String email);
}
