package com.livo.library_service.reeding_register.services;

import com.livo.library_service.library.services.FindBookByIdUseCase;
import com.livo.library_service.reeding_register.ReadingLogRepository;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateProgressService {
    @Autowired
    FindBookByIdUseCase findBookByIdUseCase;

    @Autowired
    ReadingLogRepository readingLogRepository;

    public Double getReedingProgresByPagesRead(Integer pagesRead, String bookId){
        BookSummaryResponse book = findBookByIdUseCase.execute(bookId);
        double percentage = 0.0;
        if (book.pageCount() != null && book.pageCount() > 0 && pagesRead != null) {
            percentage = (pagesRead * 100.0) / book.pageCount();
        }
        return percentage;
    }
}
