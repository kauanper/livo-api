package com.livo.library_service.shelf.bookShelf.dto;

import com.livo.library_service.shelf.entity.BookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookShelfPostDto(
        @NotBlank(message = "Book ID is required") Long bookId,

        @NotNull(message = "Status is required") BookStatus status) {
}
