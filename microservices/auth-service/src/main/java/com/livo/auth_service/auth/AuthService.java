package com.livo.auth_service.auth;

import com.livo.auth_service.auth.dto.LoginRequest;
import com.livo.auth_service.auth.dto.LoginResponse;
import com.livo.auth_service.auth.token.GenerateToken;
import com.livo.auth_service.refresh_token.RefreshTokenRepository;
import com.livo.auth_service.user_client.UserClient;
import com.livo.auth_service.refresh_token.RefreshToken;
import com.livo.auth_service.user_client.dto.UserAuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GenerateToken generateToken;
    private final UserClient userClient;
    private final RefreshTokenRepository refreshTokenRepository;
    
    @Value("${auth.jwt.access-token-exp-ms:900000}")
    private Long accessExpMs;
    
    @Value("${auth.jwt.refresh-token-exp-ms:604800000}")
    private Long refreshExpMs;

    public LoginResponse login(LoginRequest req) {
        var user = userClient.authenticate(new UserAuthRequest(req.email(), req.password()));
        if (user == null || user.getBody() == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        String accessToken = generateToken.generateAccessToken(
                String.valueOf(user.getBody().getId()),
                user.getBody().getEmail(),
                user.getBody().getUsername()
        );

        String refresh = UUID.randomUUID().toString();
        String hash = DigestUtils.sha256Hex(refresh);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(user.getBody().getId().toString());
        refreshToken.setTokenHash(hash);
        refreshToken.setDevice(null);
        refreshToken.setIssuedAt(Instant.now());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpMs));
        refreshToken.setRevoked(false);
        refreshTokenRepository.save(refreshToken);

        long accessExpSeconds = this.accessExpMs / 1000;
        long refreshExpSeconds = refreshExpMs / 1000;

        return new LoginResponse(accessToken, accessExpSeconds, refresh, refreshExpSeconds);
    }
}
