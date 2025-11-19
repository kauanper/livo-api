package com.livo.library_service.library.dtos.association;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociationRegisterDTO {
    @NotNull(message = "UserId é obrigatório")
    private UUID userId;

    @NotNull(message = "BookId é obrigatório")
    private String bookId;

    private String thumbnail;

    @NotNull(message = "Title é obrigatório")
    private String title;
}
