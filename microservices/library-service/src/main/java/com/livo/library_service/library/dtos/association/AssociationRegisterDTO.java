package com.livo.library_service.library.dtos.association;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociationRegisterDTO {
    @NotBlank(message = "BookId não pode estar vazio")
    @Size(max = 50, message = "BookId não pode ter mais que 50 caracteres")
    private String bookId;

    @Pattern(
            regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$",
            message = "Thumbnail deve ser uma URL válida"
    )
    private String thumbnail;

    @NotBlank(message = "Título não pode estar vazio")
    @Size(max = 200, message = "Título não pode ter mais que 200 caracteres")
    private String title;
}

