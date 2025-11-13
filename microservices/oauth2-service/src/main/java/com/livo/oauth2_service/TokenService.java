package com.livo.oauth2_service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TokenService {

    // Chave secreta para assinar o token (em produção, guarde em local seguro)
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String gerarTokenDeAcesso(String email) {
        long agora = System.currentTimeMillis();
        long expiracao = 1000 * 60 * 60; // 1 hora

        return Jwts.builder()
                .setSubject(email)        // Identidade do usuário
                .setIssuedAt(new Date(agora))
                .setExpiration(new Date(agora + expiracao))
                .claim("role", "USER")    // Exemplo de claim extra
                .signWith(key)
                .compact();
    }

    // Método para validar token (opcional)
    public static boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

