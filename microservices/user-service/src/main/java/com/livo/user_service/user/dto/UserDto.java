package com.livo.user_service.user.dto;

import java.util.UUID;

/**
 * DTO para representar dados do usuário em comunicação entre serviços.
 * IMPORTANTE: Nunca inclui senha por questões de segurança.
 */
public record UserDto(
    UUID id,
    String username,
    String email,
    String profilePictureUrl
) {
}

