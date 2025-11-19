package com.livo.library_service.library.mappers;

import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.AssociationResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AssociationMappers {
    public static UserBookEntity toEntity(AssociationRegisterDTO dto) {
        if (dto == null) {
            return null;
        }

        UserBookEntity entity = new UserBookEntity();
        entity.setUserId(dto.getUserId());
        entity.setBookId(dto.getBookId());
        entity.setThumbnail(dto.getThumbnail());
        entity.setReadingProgress(0); //user não começou a ler
        entity.setPersonalRatting(null); //user não deu seu voto pessoal
        entity.setTitle(dto.getTitle());
        // entity.setStatus(ainda não implementado

        return entity;
    }

    public static AssociationResponseDTO toResponseDTO(UserBookEntity entity) {
        if (entity == null) {
            return null;
        }

        return new AssociationResponseDTO(
                entity.getId(),
                entity.getThumbnail(),
                entity.getReadingProgress(),
                entity.getPersonalRatting()
        );
    }

}
