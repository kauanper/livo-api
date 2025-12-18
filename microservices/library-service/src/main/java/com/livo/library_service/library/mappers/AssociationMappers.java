package com.livo.library_service.library.mappers;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.association.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.reeding_register.services.CalculateProgressService;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import com.livo.library_service.shared.globalExceptions.custon.ResourceNotFoundException;
import com.livo.library_service.shelf.bookShelf.BookShelf;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public class AssociationMappers {

    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private CalculateProgressService calculateProgressService;

    public UserBookEntity toEntity(AssociationRegisterDTO dto, BookSummaryResponse bookDto, UUID userId) {
        if (dto == null) {
            return null;
        }

        UserBookEntity entity = new UserBookEntity();
        entity.setUserId(userId);
        entity.setBookId(dto.bookId());
        entity.setBookStatus((dto.bookStatus()));
        entity.setThumbnail(bookDto.thumbnail());
        entity.setPageCount(bookDto.pageCount());
        entity.setPersonalRatting(null);//user não deu seu voto pessoal
        entity.setTitle(bookDto.title());

        return entity;
    }

    public AssociationResponseDTO toResponseDTO(UserBookEntity entity) {
        if (entity == null) {
            return null;
        }

        return new AssociationResponseDTO(
                entity.getId(),
                entity.getBookId(),
                entity.getBookStatus(),
                entity.getThumbnail(),
                entity.getTitle(),
                calculateProgressService.getReadingProgressByLibraryBookId(
                        entity.getId(),
                        entity.getPageCount(),
                        entity.getUserId()
                ),
                entity.getPersonalRatting() //implementar ainda
        );
    }

    public AssociationResponseDTO toResponseDTO(BookShelf entity) {
        UserBookEntity book = libraryRepository
                .findById(entity.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book ID", "O Livro com o ID: " + entity.getBookId() + " não foi encontrado."));

        return new AssociationResponseDTO(
                book.getId(),
                book.getBookId(),
                book.getBookStatus(),
                book.getThumbnail(),
                book.getTitle(),
                calculateProgressService.getReadingProgressByLibraryBookId(
                        book.getId(),
                        book.getPageCount(),
                        book.getUserId()
                ),
                book.getPersonalRatting()
        );
    }
}
