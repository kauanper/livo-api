package com.livo.auth_service.auth.dto;

public record LoginResponse(
        String acessToken,
        Long expiresIn,
        String refreshToken,
        Long refreshExpiresIn
) {
}
