package com.livo.user_service.user;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @Column(columnDefinition = "text") //verificar tamanho máximo de senha no requisito.
    @NotBlank
    private String password;

    @NotNull
    @Column(unique = true, columnDefinition = "text")
    @NotBlank
    private String email;

    @Column(nullable = true, columnDefinition = "text")
    private String profilePictureUrl;
}
