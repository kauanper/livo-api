package com.livo.oauth2_service.controller;

import com.livo.oauth2_service.model.UserInfo;
import com.livo.oauth2_service.model.dtos.AuthRequest;
import com.livo.oauth2_service.model.dtos.AuthResponse;
import com.livo.oauth2_service.service.GoogleAuthService;
import com.livo.oauth2_service.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final GoogleAuthService googleAuthService;

    public AuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    @PostMapping("/google-login")
    public ResponseEntity<AuthResponse> loginWithGoogle(@RequestBody AuthRequest request) {
        try {
            AuthResponse response = googleAuthService.loginWithGoogle(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/")
    public String home() {
        return "<h2>OAuth2 Service is running. <a href='/oauth2/authorization/google'>Login with Google</a></h2>";
    }
}
