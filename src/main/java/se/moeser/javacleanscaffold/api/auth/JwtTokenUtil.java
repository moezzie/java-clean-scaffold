package se.moeser.javacleanscaffold.api.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenUtil {
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    @Value("${app.jwt.lifetime}")
    private int TOKEN_LIFETIME;

    public long extractUserId(String token) {
       String userIdStr = extractClaim(token, Claims::getSubject);

       long userId = Long.parseLong(userIdStr);

       return userId;
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(ApiUserPrincipal userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // FIXME - add user role to claims
        return createToken(claims, "" + userDetails.getId());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * this.TOKEN_LIFETIME))
                .signWith(SignatureAlgorithm.HS256, this.SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, ApiUserPrincipal userDetails) {
        final long userId = this.extractUserId(token);
        return userId == userDetails.getId() && !this.isTokenExpired(token);
    }
}
