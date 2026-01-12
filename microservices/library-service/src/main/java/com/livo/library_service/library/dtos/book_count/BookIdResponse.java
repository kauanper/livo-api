package com.livo.library_service.library.dtos.book_count;

import com.livo.library_service.library.BookStatus;

import java.math.BigDecimal;

public record BookIdResponse(
        String bookId,
        Long userBookId,
        BookStatus bookStatus,
        BigDecimal progress,
        Integer ratingPersonal,
        Double averageRatingOnLivo
){
}
