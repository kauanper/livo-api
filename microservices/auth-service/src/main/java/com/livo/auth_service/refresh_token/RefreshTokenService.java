package com.livo.auth_service.refresh_token;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(String userId) {
        var token = refreshTokenRepository.findByUserId(userId).orElse(new RefreshToken());
        token.setUserId(userId);
        token.setIssuedAt(Instant.now());
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        token.setTokenHash(UUID.randomUUID().toString());
        token.setRevoked(false);
        return refreshTokenRepository.save(token);
    }

    public boolean isTokenExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }
}
