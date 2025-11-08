package com.livo.auth_service.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshRequest(
        @NotBlank(message = "O token de atualizacao deve ser informado")
        String refreshToken,

        @NotNull(message = "O id do usuário deve ser informado")
        Long userId
) {
}
