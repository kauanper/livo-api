package com.livo.library_service.library.dtos.association;

import com.livo.library_service.library.BookStatus;

import java.math.BigDecimal;

public record AssociationResponseDTO(
        Long id,
        String bookId,
        BookStatus bookStatus,
        String thumbnail,
        String title,
        BigDecimal readingProgress,
        Integer personalRatting
) {
}
//dto que vai ser listado no front