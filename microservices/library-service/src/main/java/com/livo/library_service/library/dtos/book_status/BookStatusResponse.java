package com.livo.library_service.library.dtos.book_status;

import com.livo.library_service.library.BookStatus;

public record BookStatusResponse(
        String bookId,
        BookStatus bookStatus
) {
}

