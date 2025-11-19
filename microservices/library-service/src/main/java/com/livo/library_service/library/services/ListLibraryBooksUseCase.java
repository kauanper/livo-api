package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.AssociationResponseDTO;
import com.livo.library_service.library.mappers.AssociationMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListLibraryBooksUseCase {

    private final LibraryRepository libraryRepository;
    private final AssociationMappers associationMappers;

    public List<AssociationResponseDTO> execute(UUID userId) {
        List<UserBookEntity> entities = libraryRepository.findAllByUserId(userId);

        return entities.stream()
                .map(AssociationMappers::toResponseDTO)
                .collect(Collectors.toList());
    }
}
