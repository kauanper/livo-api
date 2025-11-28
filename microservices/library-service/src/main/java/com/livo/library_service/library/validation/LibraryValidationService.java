package com.livo.library_service.library.validation;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.shared.globalExceptions.custon.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class LibraryValidationService {
    @Autowired
    LibraryRepository libraryRepository;

    public UserBookEntity validateUserBookBelongsToUser(UUID userId, Long userBookId){
        return libraryRepository.findByIdAndUserId(userBookId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("userBookId", "Essa associação de livro não foi encontrada para o usuário autenticado"));
    }

    public UserBookEntity validateToPatch(UUID userId, Long userBookId){
        return validateUserBookBelongsToUser(userId, userBookId);
    }
}
