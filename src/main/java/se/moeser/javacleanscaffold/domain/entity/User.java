package se.moeser.javacleanscaffold.domain.entity;

import se.moeser.javacleanscaffold.domain.valueobject.Email;
import se.moeser.javacleanscaffold.domain.valueobject.Password;
import se.moeser.javacleanscaffold.domain.valueobject.Role;
import se.moeser.javacleanscaffold.domain.valueobject.Username;

public class User {

    private long id;
    private Email email;
    private Username username;
    private Password password;
    private Role role;

    public User(long id, Email email, Username username, Password password, Role role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(long id, Email email, Username username, Password password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = new Role(Role.USER);
    }

    public User(long id, Email email, Username username) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = new Role("ROLE_USER");
    }

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
