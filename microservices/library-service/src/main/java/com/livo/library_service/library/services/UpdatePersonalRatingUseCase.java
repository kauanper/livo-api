package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.PersonalRatingUpdateDTO;
import com.livo.library_service.library.validation.LibraryValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdatePersonalRatingUseCase {

    private final LibraryRepository libraryRepository;
    private final LibraryValidationService libraryValidationService;

    @Transactional
    public void execute(UUID userId, String bookId, Integer personalRating) {
        log.info("Atualizando personalRating para livro {} do usuário {} com nota {}", bookId, userId, personalRating);
        
        UserBookEntity userBook = libraryRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new com.livo.library_service.shared.globalExceptions.custon.ResourceNotFoundException(
                        "bookId",
                        "Livro não encontrado na biblioteca do usuário"
                ));

        userBook.setPersonalRatting(personalRating);
        libraryRepository.save(userBook);
        
        log.debug("PersonalRating atualizado para livro {} do usuário {}: {}", bookId, userId, personalRating);
    }

    @Transactional
    public void removePersonalRating(UUID userId, String bookId) {
        log.info("Removendo personalRating para livro {} do usuário {}", bookId, userId);
        
        UserBookEntity userBook = libraryRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new com.livo.library_service.shared.globalExceptions.custon.ResourceNotFoundException(
                        "bookId",
                        "Livro não encontrado na biblioteca do usuário"
                ));

        userBook.setPersonalRatting(null);
        libraryRepository.save(userBook);
        
        log.debug("PersonalRating removido para livro {} do usuário {}", bookId, userId);
    }
}


