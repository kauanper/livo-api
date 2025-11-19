package com.livo.library_service.library.dtos;

public record AssociationResponseDTO(
        Long id,
        String thumbnail,
        Integer readingProgress,
        Integer personalRatting
) {
}
//dto que vai ser listado no front