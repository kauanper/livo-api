package com.livo.library_service.shelf.entity.dtos;

import java.util.List;
import java.util.UUID;

public record ShelfPostDto(
        String name,
        String description,
        List<UUID> books
) {
}
