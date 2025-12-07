package com.livo.library_service.reeding_register.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReadingLogResponseDTO(
        Long id,
        Long libraryBookId,
        String title,
        String text,
        LocalDateTime time,
        Integer pagesRead,
        BigDecimal percentageRead
) {
}
