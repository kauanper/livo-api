package com.livo.library_service.reeding_register.validation;

import com.livo.library_service.library.BookStatus;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.validation.LibraryValidationService;
import com.livo.library_service.reeding_register.dtos.ReadingLogRegisterDTO;
import com.livo.library_service.shared.clients.BookClient;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import com.livo.library_service.shared.globalExceptions.custon.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReedingRegisterValidationService {
    @Autowired
    private LibraryValidationService libraryValidator;

    @Autowired
    private BookClient bookClient;

    public void validateBookCanReciveReedingRegisters(UserBookEntity userBook){
        if(userBook.getBookStatus().equals(BookStatus.QUERO_LER))
            throw new BusinessRuleException("bookStatus", "Não é possível registrar resenhas para um livro com status QUERO_LER.");
    }
    public void validatePagesRead(Integer pagesRead, String bookId){
        BookSummaryResponse book = bookClient.getBookByIdInternal(bookId);
        if (pagesRead < 0)
            throw new BusinessRuleException("pagesRead", "pagesRead não pode ser negativo");
        if (pagesRead > book.pageCount())
            throw new BusinessRuleException("pagesRead", "pagesRead não pode ser maior que o total de páginas do livro. Total de páginas do livro: " + book.pageCount());
    }

    public void validateToAddReedingRegister(ReadingLogRegisterDTO dto, UUID userId){
        UserBookEntity userBook = libraryValidator.validateUserBookBelongsToUser(userId, dto.libraryBookId());
        validatePagesRead(dto.pagesRead(), userBook.getBookId());
        validateBookCanReciveReedingRegisters(userBook);
    }
}
