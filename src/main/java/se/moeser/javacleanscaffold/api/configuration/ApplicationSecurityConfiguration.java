package se.moeser.javacleanscaffold.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import se.moeser.javacleanscaffold.api.auth.ApiUserDetailsService;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApiUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // Make all endpoints private by default
                .antMatcher("/*")
                    .authorizeRequests()
                        // Public endpoints
                        .antMatchers(HttpMethod.GET, this.publicGetEndpoints())
                        .permitAll()
                        .antMatchers(HttpMethod.POST, this.publicPostEndpoints())
                        .permitAll()
                    .anyRequest().authenticated();
    }


    protected String[] publicPostEndpoints() {
        return new String[]{
                "/user",
                "/user/authenticate"
        };

    }

    protected String[] publicGetEndpoints() {
        return new String[]{
                "/user/[0-9]+"
        };
    }
}
