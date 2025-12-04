package com.livo.library_service.library.mappers;

import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.association.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AssociationMappers {
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
}
