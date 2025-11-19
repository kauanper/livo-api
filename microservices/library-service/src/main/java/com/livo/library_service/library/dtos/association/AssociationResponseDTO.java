package com.livo.library_service.library.dtos.association;

public record AssociationResponseDTO(
        Long id,
        String thumbnail,
        Integer readingProgress,
        Integer personalRatting
) {
}
//dto que vai ser listado no front