package com.livo.library_service.shelf.entity.dtos;

import java.util.List;

public record ShelfPostDto(
        String name,
        String description,
        List<Long> books
) {
}
