package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.dtos.AssociationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListLibraryBooksUseCase {

    private final LibraryRepository libraryRepository;

    public List<AssociationResponseDTO> execute(UUID userId) {

    }
}
