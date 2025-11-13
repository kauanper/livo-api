package com.livo.oauth2_service.controller;

import com.livo.oauth2_service.model.UserInfo;
import com.livo.oauth2_service.model.dtos.AuthRequest;
import com.livo.oauth2_service.model.dtos.AuthResponse;
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



    @GetMapping("/")
    public String home() {
        return "<h2>OAuth2 Service is running. <a href='/oauth2/authorization/google'>Login with Google</a></h2>";
    }
}
