package com.livo.oauth2_service.controller;

import com.livo.oauth2_service.model.UserInfo;
import com.livo.oauth2_service.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuth2UserService oAuth2UserService;

    @GetMapping("/api/auth/user")
    public UserInfo getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2UserService.extractUserInfo(oAuth2User);
    }

    @GetMapping("/")
    public String home() {
        return "<h2>OAuth2 Service is running. <a href='/oauth2/authorization/google'>Login with Google</a></h2>";
    }
}
