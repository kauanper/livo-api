package com.livo.oauth2_service.controller;

import com.livo.oauth2_service.model.UserInfo;
import com.livo.oauth2_service.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/api/auth/user")
    public Map<String, Object> getUser(OAuth2AuthenticationToken authentication) {
        OAuth2User oAuth2User = authentication.getPrincipal();
        UserInfo user = oAuth2UserService.extractUserInfo(oAuth2User);

        // recupera o access_token do Google
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()
        );

        String accessToken = client.getAccessToken().getTokenValue();

        return Map.of(
                "user", user,
                "access_token", accessToken
        );
    }

    @GetMapping("/")
    public String home() {
        return "<h2>OAuth2 Service is running. <a href='/oauth2/authorization/google'>Login with Google</a></h2>";
    }
}
