package JForce.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    /**
     * First of all I define that My JWT key For using that Hash-256 Strategy and every 1 hour our token will be reset
     * so We Can Optimize that easily our secure that token to privacy issue.
     */
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long ExpiredTime = 1000 * 60 * 60;

    /**
     * In that here Each user have got a role as we known that so we need define each user for generate token to enter via system
     * so we can easily detect who is who and As I describe above after 1 hour due to privacy issue reseted.
     * @param username
     * @param role
     * @return
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ExpiredTime))
                .signWith(key)
                .compact();
    }

    /**
     * Basically Validatefor token who'm entered system with which token basic on java as known encrpytion hashing
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String extractRole(String token) {
        return (String) Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role");
    }
}
