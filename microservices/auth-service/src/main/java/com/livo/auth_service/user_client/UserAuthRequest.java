package com.livo.auth_service.user_client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO para autenticação de usuário.
 * A senha é enviada apenas durante a requisição HTTP (via TLS)
 * e nunca é armazenada no Auth-Service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthRequest {



    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    private String password;
}
