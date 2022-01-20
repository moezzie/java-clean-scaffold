package se.moeser.javacleanscaffold.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import se.moeser.javacleanscaffold.api.auth.ApiUserDetailsService;
import se.moeser.javacleanscaffold.api.filters.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApiUserDetailsService userDetailsService;

    @Autowired
    JwtRequestFilter jwtRequstFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // FIXME
        return NoOpPasswordEncoder.getInstance();
        // return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    /**
     * Creates the AuthenticationManager so that we can use it in the /authentication endpoint
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
                    .anyRequest().fullyAuthenticated()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.addFilterBefore(this.jwtRequstFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
    }

    protected String[] publicPostEndpoints() {
        return new String[]{
                "/user",
                "/user/authenticate"
        };

    }

    protected String[] publicGetEndpoints() {
        return new String[]{
                // "/user/[0-9]+"
        };
    }
}
