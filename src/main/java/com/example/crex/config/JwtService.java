package com.example.crex.config;

import com.example.crex.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

//@Service
/*public class JwtService {

    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours
    private static final String SECRET = "a9f3JkL0wQ8pS2xN7rV5yB1hT6dC4uM9zE3tW0qP6oR9iU2lK8mY5gH7bL4sD1f";
//    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getUserRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractToken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        return (auth != null && auth.startsWith("Bearer ")) ? auth.substring(7) : null;
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
 */

@Service
public class JwtService {

    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours
    private static final String SECRET =
            "a9f3JkL0wQ8pS2xN7rV5yB1hT6dC4uM9zE3tW0qP6oR9iU2lK8mY5gH7bL4sD1f";

    /**
     * Generate token using full User object so we can store role in claim
     */
    public String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getUserRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extract username (email) from token
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Validate full token with UserDetails
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Check if token is expired
     */
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * Extract all claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractToken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            return auth.substring(7);
        }
        return null;
    }

}
