package com.livo.library_service.library.dtos.association;

import com.livo.library_service.library.BookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AssociationRegisterDTO(
        @NotBlank(message = "BookId não pode estar vazio")
        @Size(max = 50, message = "BookId não pode ter mais que 50 caracteres")
        String bookId,

        @NotNull(message = "BookStatus não pode estar vazio")
        BookStatus bookStatus
) {
}

