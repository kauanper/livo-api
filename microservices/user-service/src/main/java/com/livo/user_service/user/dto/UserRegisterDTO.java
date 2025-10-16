package com.livo.user_service.user.dto;

import com.livo.user_service.utils.notations.nullOrNotBlank.NullOrNotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(
    @NotBlank(message = "O nome de usuário não pode estar em branco")
    String name,
    @NotBlank(message = "A senha não pode estar em branco")
    String password,
    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "O e-mail é obrigatório")
    String email,
    @NullOrNotBlank()
    String profilePictureUrl
    ) {}
