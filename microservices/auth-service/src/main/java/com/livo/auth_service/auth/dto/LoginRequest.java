package com.livo.auth_service.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "O nome de usuário deve ser informado!")
        String email,

        @NotBlank(message = "A senha de acesso deve ser informada!")
        String password
) {
}
