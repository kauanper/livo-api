package com.livo.oauth2_service.model.dtos;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken; //Token OAuth2 para o Google Books
    private String tokenType = "Bearer";
    private long expiresIn;
}
