package com.livo.library_service.library.dtos.association;

public record AssociationResponseDTO(
        Long id,
        String thumbnail,
        String title,
        Integer readingProgress,
        Integer personalRatting
) {
}
//dto que vai ser listado no front