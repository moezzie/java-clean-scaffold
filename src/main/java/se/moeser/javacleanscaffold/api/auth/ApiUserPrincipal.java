package se.moeser.javacleanscaffold.api.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUserResponseInterface;

import java.util.*;

public class ApiUserPrincipal implements UserDetails {

    private AuthenticateUserResponseInterface user;

    public ApiUserPrincipal(AuthenticateUserResponseInterface user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(this.user.getRole()));

        return list;
    }

    public boolean hasRole(String role) {
        return this.getAuthorities().contains(role);
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    public long getId() {
        return this.user.getId();
    }

    public String getRole() {
        return this.user.getRole();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
