package com.livo.library_service.shelf.bookShelf.dto;

import com.livo.library_service.shelf.entity.BookStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookShelfDto(
        UUID id,
        Long bookId,
        BookStatus status,
        LocalDateTime addedAt,
        Float rating) {
}
