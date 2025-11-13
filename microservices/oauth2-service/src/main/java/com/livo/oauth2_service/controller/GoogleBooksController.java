package com.livo.oauth2_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
public class GoogleBooksController {

    private final WebClient webClient;

    @Value("${google.client-id}")
    private String clientId;

    public GoogleBooksController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    /**
     * Endpoint que retorna informações do usuário autenticado
     */
    @GetMapping("/user")
    public Map<String, Object> userInfo(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
                                        OAuth2User oauth2User) {
        // Dados do usuário
        return Map.of(
                "name", oauth2User.getAttribute("name"),
                "email", oauth2User.getAttribute("email"),
                "picture", oauth2User.getAttribute("picture"),
                "accessToken", authorizedClient.getAccessToken().getTokenValue()
        );
    }

    /**
     * Endpoint para listar prateleiras do Google Books
     */
    @GetMapping("/bookshelves")
    public Map listBookshelves(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        return webClient.get()
                .uri("https://www.googleapis.com/books/v1/mylibrary/bookshelves")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
