package com.livo.auth_service.user_client.dto;

import java.util.UUID;

/**
 * DTO para representar dados do usuário.
 * IMPORTANTE: Nunca inclui senha por questões de segurança.
 */
public record UserDto(
        UUID id,
        String username,
        String email,
        String profilePictureUrl
) {
}
