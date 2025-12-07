package com.livo.library_service.reeding_register.validation;

import com.livo.library_service.library.BookStatus;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.validation.LibraryValidationService;
import com.livo.library_service.shared.globalExceptions.custon.InvalidBookStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReedingRegisterValidationService {
    @Autowired
    private LibraryValidationService libraryValidator;

    public void validateBookCanReciveReedingRegisters(UserBookEntity userBook){
        if(userBook.getBookStatus().equals(BookStatus.QUERO_LER))
            throw new InvalidBookStatusException("bookStatus", "Não é possível registrar resenhas para um livro com status QUERO_LER.");
    }

    public void validateToAddReedingRegister(Long userBookId, UUID userId){
        UserBookEntity userBook = libraryValidator.validateUserBookBelongsToUser(userId, userBookId);
        validateBookCanReciveReedingRegisters(userBook);
    }
}
