package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.association.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.library.mappers.AssociationMappers;
import com.livo.library_service.shared.clients.BookClient;
import com.livo.library_service.shared.clients.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAssociationUseCase {

    private final LibraryRepository libraryRepository;
    private final AssociationMappers associationMappers;
    private final UserClient userClient;
    private final BookClient bookClient;

    public AssociationResponseDTO execute(AssociationRegisterDTO register) {



        UserBookEntity entity = associationMappers.toEntity(register);
        UserBookEntity saved = libraryRepository.save(entity);

        return AssociationMappers.toResponseDTO(saved);
    }
}
