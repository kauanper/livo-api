package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.custonExceptions.AssociationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteByIdUseCase {

    private final LibraryRepository libraryRepository;

    public void execute(Long userBookId) {

        var entity = libraryRepository.findById(userBookId)
                .orElseThrow(() -> new AssociationNotFoundException(userBookId));

        libraryRepository.deleteById(entity.getId());
    }
}
