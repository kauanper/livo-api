package com.livo.auth_service.auth;

import com.livo.auth_service.auth.dto.LoginRequest;
import com.livo.auth_service.auth.dto.LoginResponse;
import com.livo.auth_service.auth.token.GenerateToken;
import com.livo.auth_service.refresh_token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GenerateToken generateToken;

    public LoginResponse login(LoginRequest req) {
        var user = userClient.authenticate(req.email(), req.password());
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        String accessToken = generateToken.generateAccessToken(user.getEmail(), user.getPassword());
        String refresh = UUID.randomUUID().toString();
        String hash = HashUtil.sha256Hex(refresh);
        RefreshToken rt = new RefreshToken(UUID.randomUUID().toString(), user.getId(), hash, null, Instant.now(),
                Instant.now().plusMillis(refreshExpMs), false);
        refreshRepo.save(rt);
        return new LoginResponse(accessToken, accessExpSeconds, refresh, refreshExpSeconds);

    }
}
