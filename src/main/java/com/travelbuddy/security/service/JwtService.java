package com.travelbuddy.security.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
@Service
public class JwtService {

	@Value("${jwt.secret}")
    private String secret;
    
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    // METODO INTERNO
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    // EMAIL (SUBJECT)
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    public Long extractAuthUserId(String token) {
        //Object claim = jwt.getClaim("userId");
        Object claim = extractAllClaims(token).get("id");
        if (claim instanceof Integer value) {
            return value.longValue();
        }
        if (claim instanceof Long value) {
            return value;
        }
        if (claim instanceof String value) {
            return Long.parseLong(value);
        }
        throw new RuntimeException("Claim id non valido");
    }
    // RUOLI
    public List<String> extractRoles(String token) {
        List<?> roles = extractAllClaims(token).get("roles", List.class);
        return roles.stream()
                .map(Object::toString)
                .toList();
    }
    // VALIDAZIONE TOKEN
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
	
	
}
