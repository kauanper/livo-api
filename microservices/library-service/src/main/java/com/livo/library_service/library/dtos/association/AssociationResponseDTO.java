package com.livo.library_service.library.dtos.association;

import com.livo.library_service.library.BookStatus;

public record AssociationResponseDTO(
        Long id,
        BookStatus bookStatus,
        String thumbnail,
        String title,
        Integer readingProgress,
        Integer personalRatting
) {
}
//dto que vai ser listado no front