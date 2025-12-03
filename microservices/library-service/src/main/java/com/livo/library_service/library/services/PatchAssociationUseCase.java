package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.BookStatusPatchDTO;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.library.mappers.AssociationMappers;
import com.livo.library_service.library.validation.LibraryValidationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatchAssociationUseCase {
    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryValidationService libraryValidationService;

    @Transactional
    public AssociationResponseDTO execute(UUID userId, Long userBookId, BookStatusPatchDTO dto){
        UserBookEntity userBook = libraryValidationService.validateToPatch(userId, userBookId);
        userBook.setBookStatus(dto.bookStatus());
        libraryRepository.save(userBook);
        return AssociationMappers.toResponseDTO(userBook);
    }
}
