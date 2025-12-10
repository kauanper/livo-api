package com.livo.library_service.reeding_register.services;

import com.livo.library_service.library.services.FindBookByIdUseCase;
import com.livo.library_service.reeding_register.ReadingLog;
import com.livo.library_service.reeding_register.ReadingLogRepository;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class CalculateProgressService {
    @Autowired
    FindBookByIdUseCase findBookByIdUseCase;

    @Autowired
    ReadingLogRepository readingLogRepository;

    public BigDecimal getReedingProgresByPagesRead(Integer pagesRead, String bookId){
        BookSummaryResponse book = findBookByIdUseCase.execute(bookId);
        BigDecimal percentage = BigDecimal.ZERO;
        if (book.pageCount() != null && book.pageCount() > 0 && pagesRead != null) {
            percentage = BigDecimal.valueOf((double) pagesRead / book.pageCount() * 100)
                    .setScale(0, RoundingMode.HALF_DOWN);
        }
        return percentage;
    }

    public BigDecimal getReedingProgresByLibraryBookId(Long userBookId){
        Optional<ReadingLog> log = readingLogRepository.findMaxByLibraryBookId(userBookId);
        BigDecimal percentage = BigDecimal.ZERO;
        if (book.pageCount() != null && book.pageCount() > 0 && pagesRead != null) {
            percentage = BigDecimal.valueOf((double) pagesRead / book.pageCount() * 100)
                    .setScale(0, RoundingMode.HALF_DOWN);
        }
        return percentage;
    }
}
