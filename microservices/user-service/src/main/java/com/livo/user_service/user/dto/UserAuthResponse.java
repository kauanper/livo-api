package com.livo.user_service.user.dto;

import java.util.UUID;

/**
 * DTO de resposta para autenticação interna.
 * IMPORTANTE: Nunca inclui senha por questões de segurança.
 */
public record UserAuthResponse(
    UUID id,
    String email,
    String username,
    String profilePictureUrl
) {
}

