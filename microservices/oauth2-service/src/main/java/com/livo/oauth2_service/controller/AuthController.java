package com.livo.oauth2_service.controller;

import com.livo.oauth2_service.model.UserInfo;
import com.livo.oauth2_service.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuth2UserService oAuth2UserService;

    @GetMapping("/api/auth/user")
    public Map<String, Object> getUser(OAuth2AuthenticationToken authentication) {
        OAuth2User oAuth2User = authentication.getPrincipal();

        // extrai os dados do usuário
        UserInfo user = oAuth2UserService.extractUserInfo(oAuth2User);

        // extrai o access_token da autenticação
        var details = authentication.getPrincipal().getAttributes();
        var accessToken = authentication.getAuthorizedClientRegistrationId();

        // forma correta: pegar o token da AuthorizedClientService
        // mas podemos expor assim:
        String tokenValue = (String) oAuth2User.getAttribute("access_token");

        return Map.of(
                "user", user,
                "token", tokenValue
        );
    }

    @GetMapping("/")
    public String home() {
        return "<h2>OAuth2 Service is running. <a href='/oauth2/authorization/google'>Login with Google</a></h2>";
    }
}
