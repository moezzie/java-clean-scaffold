package se.moeser.javacleanscaffold.infrastructure.persistence.dto;

import se.moeser.javacleanscaffold.domain.entity.User;
import se.moeser.javacleanscaffold.domain.exception.InvalidEmailException;
import se.moeser.javacleanscaffold.domain.exception.InvalidUsernameException;
import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Username;

import jakarta.persistence.*;

@Entity(name="User")
@Table(name = "users")
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    public UserDto() {}

    public UserDto(User user) {
        if (user.getId() > -1) {
            this.id = user.getId();
        }
        this.email = user.getEmail().toString();
        this.username = user.getUsername().toString();
        this.password = user.getPassword().toString();
        this.role = user.getRole().toString();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() { return this.role; }

    public void setRole(String role) { this.role = role; }

    public User getDomainEntity() throws InvalidUsernameException, InvalidEmailException {
        long id = this.getId();

        Email email = new Email(this.getEmail());
        Username username = new Username(getUsername());

        return new User(id, email, username);
    }
}
