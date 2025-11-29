package com.livo.library_service.shelf.entity.dtos;

import com.livo.library_service.shelf.bookShelf.dto.BookShelfDto;

import java.util.List;

public record ShelfDto(
        String name,
        Integer quantity,
        List<BookShelfDto> bookShelfDto
) {
}
