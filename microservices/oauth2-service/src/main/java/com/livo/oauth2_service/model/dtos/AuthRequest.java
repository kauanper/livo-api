package com.livo.oauth2_service.model.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    private String idToken; // Token recebido do front (JWT do Google)
    private String email;
    private String name;
    private String photoUrl;
}
