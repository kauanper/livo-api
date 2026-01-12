package com.livo.book_service.dtos;

import java.math.BigDecimal;

public record LibraryRegistration(
        Long userBookId,
        BookStatus bookStatus,
        BigDecimal progress,
        Integer ratingPersonal
) {
}
