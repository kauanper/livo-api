package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.PersonalRatingUpdateDTO;
import com.livo.library_service.library.validation.LibraryValidationService;
import com.livo.library_service.shelf.bookShelf.BookShelf;
import com.livo.library_service.shelf.bookShelf.BookShelfRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdatePersonalRatingUseCase {

    private final LibraryRepository libraryRepository;
    private final LibraryValidationService libraryValidationService;
    private final BookShelfRepository bookShelfRepository;

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

        // Atualiza o rating em todas as prateleiras onde o livro está
        // (regra de negócio: mudança na biblioteca reflete na prateleira)
        updateBookRatingInShelves(userBook.getId(), personalRating != null ? personalRating.floatValue() : null);
        
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

        // Remove o rating em todas as prateleiras onde o livro está
        updateBookRatingInShelves(userBook.getId(), null);
        
        log.debug("PersonalRating removido para livro {} do usuário {}", bookId, userId);
    }

    /**
     * Atualiza o rating do livro em todas as prateleiras onde ele está.
     */
    private void updateBookRatingInShelves(Long bookId, Float rating) {
        List<BookShelf> bookShelves = bookShelfRepository.findAllByBookId(bookId);
        for (BookShelf bookShelf : bookShelves) {
            bookShelf.setRating(rating);
        }
        bookShelfRepository.saveAll(bookShelves);
    }
}
