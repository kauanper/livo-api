package com.livo.auth_service.user_client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Resposta de autenticação do User-Service.
 * Retornado após validação bem-sucedida de credenciais.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthResponse {
    private UUID id;
    private String email;
    private String username;
    private String profilePictureUrl;
}
