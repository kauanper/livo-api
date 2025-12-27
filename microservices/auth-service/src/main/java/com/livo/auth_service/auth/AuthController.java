package com.livo.auth_service.auth;

import com.livo.auth_service.auth.dto.LoginRequest;
import com.livo.auth_service.auth.dto.LoginResponse;
import com.livo.auth_service.auth.dto.RefreshRequest;
import com.livo.auth_service.refresh_token.RefreshToken;
import com.livo.auth_service.refresh_token.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/test")
    public String testEndpoint() {
        return "Auth-Service funcionando!";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshToken> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(refreshTokenService.createRefreshToken(request.userId()));
    }
}
