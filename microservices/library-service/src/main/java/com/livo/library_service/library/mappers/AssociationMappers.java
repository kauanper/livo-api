package com.livo.library_service.library.mappers;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.association.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import com.livo.library_service.shared.globalExceptions.custon.ResourceNotFoundException;
import com.livo.library_service.shelf.bookShelf.BookShelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AssociationMappers {

    @Autowired
    private static LibraryRepository libraryRepository;

    public static UserBookEntity toEntity(AssociationRegisterDTO dto, BookSummaryResponse bookDto, UUID userId) {
        if (dto == null) {
            return null;
        }

        UserBookEntity entity = new UserBookEntity();
        entity.setUserId(userId);
        entity.setBookId(dto.bookId());
        entity.setBookStatus((dto.bookStatus()));
        entity.setThumbnail(bookDto.thumbnail());
        entity.setReadingProgress(0); //user não começou a ler
        entity.setPersonalRatting(null); //user não deu seu voto pessoal
        entity.setTitle(bookDto.title());

        return entity;
    }

    public static AssociationResponseDTO toResponseDTO(UserBookEntity entity) {
        if (entity == null) {
            return null;
        }

        return new AssociationResponseDTO(
                entity.getId(),
                entity.getBookId(),
                entity.getBookStatus(),
                entity.getThumbnail(),
                entity.getTitle(),
                entity.getReadingProgress(),
                entity.getPersonalRatting()
        );
    }

    public static AssociationResponseDTO toResponseDTO(BookShelf entity) {
        UserBookEntity book = libraryRepository
                .findById(entity.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book ID", "O Livro com o ID: " + entity.getBookId() + " não foi encontrado."));

        return new AssociationResponseDTO(
                book.getId(),
                book.getBookId(),
                book.getBookStatus(),
                book.getThumbnail(),
                book.getTitle(),
                book.getReadingProgress(),
                book.getPersonalRatting()
        );
    }
}
