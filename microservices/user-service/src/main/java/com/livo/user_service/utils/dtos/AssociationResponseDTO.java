package com.livo.user_service.utils.dtos;

import java.util.List;


public record AssociationResponseDTO(
        Long id,
        BookStatus bookStatus,
        String thumbnail,
        String title,
        Integer readingProgress,
        Integer personalRatting
) {
}
