package com.livo.library_service.library.dtos.book_count;

import com.livo.library_service.library.BookStatus;

import java.util.UUID;

public record BookIdResponse(
        String bookId,
        BookStatus bookStatus,
        Long userBookId,
        Integer readingProgress,
        Integer personalRatting,
        Integer generalRatting // no prototipo está como inteiro :b
){
}
