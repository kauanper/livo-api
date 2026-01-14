package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.BookStatusPatchDTO;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.library.mappers.AssociationMappers;
import com.livo.library_service.library.validation.LibraryValidationService;
import com.livo.library_service.shelf.bookShelf.BookShelf;
import com.livo.library_service.shelf.bookShelf.BookShelfRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatchAssociationUseCase {
    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryValidationService libraryValidationService;

    @Autowired
    AssociationMappers associationMappers;

    @Autowired
    BookShelfRepository bookShelfRepository;

    @Transactional
    public AssociationResponseDTO execute(UUID userId, Long userBookId, BookStatusPatchDTO dto){
        UserBookEntity userBook = libraryValidationService.validateToPatch(userId, userBookId);
        userBook.setBookStatus(dto.bookStatus());
        libraryRepository.save(userBook);

        // Atualiza o status em todas as prateleiras onde o livro está
        // (regra de negócio: mudança de status na biblioteca reflete na prateleira)
        updateBookStatusInShelves(userBook.getId(), dto.bookStatus());

        return associationMappers.toResponseDTO(userBook);
    }

    /**
     * Converte o BookStatus da library para o BookStatus da shelf e atualiza
     * todas as entradas do livro nas prateleiras.
     */
    private void updateBookStatusInShelves(Long bookId, com.livo.library_service.library.BookStatus libraryStatus) {
        // Converte o enum da library para o enum da shelf
        com.livo.library_service.shelf.entity.BookStatus shelfStatus = convertToShelfStatus(libraryStatus);
        
        // Busca todas as entradas do livro nas prateleiras e atualiza o status
        List<BookShelf> bookShelves = bookShelfRepository.findAllByBookId(bookId);
        for (BookShelf bookShelf : bookShelves) {
            bookShelf.setStatus(shelfStatus);
        }
        bookShelfRepository.saveAll(bookShelves);
    }

    /**
     * Converte o enum BookStatus do pacote library para o enum BookStatus do pacote shelf.
     */
    private com.livo.library_service.shelf.entity.BookStatus convertToShelfStatus(
            com.livo.library_service.library.BookStatus libraryStatus) {
        return switch (libraryStatus) {
            case QUERO_LER -> com.livo.library_service.shelf.entity.BookStatus.QUERO_LER;
            case LENDO -> com.livo.library_service.shelf.entity.BookStatus.LENDO;
            case LIDO -> com.livo.library_service.shelf.entity.BookStatus.LIDO;
            case ABANDONADO -> com.livo.library_service.shelf.entity.BookStatus.ABANDONADO;
        };
    }
}
