package com.livo.library_service.shelf.entity.dtos;

import com.livo.library_service.shelf.bookShelf.dto.BookShelfPostDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ShelfPostDto(
        @NotBlank(message = "O nome da pratelheira deve ser informado.")
        String name,

        String description,
        List<BookShelfPostDto> books
) {
}
