package com.livo.user_service.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para requisição de autenticação interna (comunicação entre serviços).
 */
public record UserAuthRequest(
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    String email,
    
    @NotBlank(message = "Senha é obrigatória")
    String password
) {
}

