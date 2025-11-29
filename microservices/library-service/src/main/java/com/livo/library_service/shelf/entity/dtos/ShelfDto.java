package com.livo.library_service.shelf.entity.dtos;

import com.livo.library_service.shelf.bookShelf.dto.BookShelfDto;

import java.util.List;
import java.util.UUID;

public record ShelfDto(
        UUID id,
        String name,
        Integer quantity,
        List<BookShelfDto> bookShelfDto
) {
}
