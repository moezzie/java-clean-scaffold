package se.moeser.javacleanscaffold.api.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.RegisteredClaims;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtTokenUtil {
    @Value("${app.jwt.secret}")
    private String SECRET_KEY = "changeme";

    @Value("${app.jwt.issuer}")
    private String ISSUER = "changeme";

    @Value("${app.jwt.lifetime}")
    private int TOKEN_LIFETIME_SECONDS = 25 * 60 * 60;

    Algorithm ALGORITHM;

    JWTVerifier VERIFIER;

    public JwtTokenUtil(@Value("${app.jwt.secret}") String secretKey) {
        this.SECRET_KEY = secretKey;
        this.ALGORITHM = Algorithm.HMAC512(secretKey);
        this.VERIFIER = JWT.require(this.ALGORITHM)
                .build();
    }

    public long extractUserId(String token) {
        var claims = extractClaims(token);
        return extractUserId(claims);
    }

    private long extractUserId(Map<String, Claim> claims) {
        var userIdStr = claims.get(RegisteredClaims.SUBJECT).asString();
        return Long.parseLong(userIdStr);
    }

    private Date extractExpiryTime(Map<String, Claim> claims) {
        return claims.get(RegisteredClaims.EXPIRES_AT).asDate();
    }

    public String extractRole(String token) {
        var claims = this.extractClaims(token);
        return claims.get("role").asString();
    }

    public Map<String, Claim> extractClaims(String token) {
        DecodedJWT decodedJWT = this.VERIFIER.verify(token);
        return decodedJWT.getClaims();
    }

    private Boolean isTokenExpired(Date expiryClaim) {
        return expiryClaim.before(new Date());
    }

    public String generateToken(ApiUserPrincipal userDetails) {
        return createToken("" + userDetails.getId(), userDetails.getRole());
    }

    private String createToken(String subject, String userRole) {
        return JWT.create()
                .withSubject(subject)
                .withIssuer(this.ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(getExpiryTime(this.TOKEN_LIFETIME_SECONDS))
                .withClaim("role", userRole)
                .sign(this.ALGORITHM);
    }

    public Boolean validateToken(String token, ApiUserPrincipal userDetails) {
        Map<String, Claim> claims = extractClaims(token);

        var userId = extractUserId(claims);
        var expiryClaim = extractExpiryTime(claims);

        return userId == userDetails.getId() && !this.isTokenExpired(expiryClaim);
    }

    private Date getExpiryTime(long lifetineSeconds) {
        return new Date(System.currentTimeMillis() + 1000 * lifetineSeconds);
    }
}
