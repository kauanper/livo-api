package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.AssociationResponseDTO;
import com.livo.library_service.library.mappers.AssociationMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAssociationUseCase {

    private final LibraryRepository libraryRepository;
    private final AssociationMappers associationMappers;


    //lembrar das validações
    public AssociationResponseDTO execute(AssociationRegisterDTO register) {

        UserBookEntity entity = associationMappers.toEntity(register);
        UserBookEntity saved = libraryRepository.save(entity);

        return AssociationMappers.toResponseDTO(saved);
    }
}
