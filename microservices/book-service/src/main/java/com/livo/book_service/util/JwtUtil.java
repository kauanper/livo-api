package com.livo.book_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${api.security.token.secret:jwt-secret-long-test-key-for-development}")
    private String secret; // carregue via application-*.properties ou env var

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extrai o userId como UUID do token. Tenta "sub" primeiro, depois "userId".
     * Lança RuntimeException (ou JwtException) se inválido.
     */
    public UUID extractUserId(String token) {
        Claims claims = extractAllClaims(token);

        // tenta sub (subject)
        String sub = claims.getSubject();
        if (sub != null && !sub.isBlank()) {
            return UUID.fromString(sub);
        }

        // tenta claim userId
        Object v = claims.get("userId");
        if (v != null) {
            return UUID.fromString(v.toString());
        }

        throw new IllegalStateException("userId not present in token claims");
    }
}
