package com.livo.library_service.library.dtos.book_count;

import com.livo.library_service.library.BookStatus;

public record BookIdResponse(
        String bookId,
        BookStatus bookStatus,
        Integer readingProgress,
        Integer personalRatting,
        Integer generalRatting // no prototipo está como inteiro :b
){
}
