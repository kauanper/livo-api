package com.livo.library_service.library.mappers;

import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.AssociationRegisterDTO;
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
        // entity.setStatus(ainda não implementado

        return entity;
    }

}
