package se.moeser.javacleanscaffold.api.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import se.moeser.javacleanscaffold.api.auth.ApiUserDetailsService;
import se.moeser.javacleanscaffold.api.auth.ApiUserPrincipal;
import se.moeser.javacleanscaffold.api.auth.JwtTokenUtil;
import se.moeser.javacleanscaffold.application.usecase.user.authenticate.AuthenticateUserResponse;
import se.moeser.javacleanscaffold.domain.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    ApiUserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        long userId = 0;
        String jwt = null;

        // Find the user id in the token payload
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            userId = jwtTokenUtil.extractUserId(jwt);
        }


        if (userId > 0 && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = new User();
            user.setId(userId);

            // Since the token is signed we can trust the user id from the payload
            // Wrap the user in an AuthenticateUserResponse without checking with the database first
            // This saves us one lookup for each authenticated request.
            ApiUserPrincipal userDetails = new ApiUserPrincipal(new AuthenticateUserResponse(user));

            if (jwtTokenUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);

    }
}
