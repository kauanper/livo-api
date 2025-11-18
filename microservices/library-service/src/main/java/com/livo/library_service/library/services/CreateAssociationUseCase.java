package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.dtos.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.AssociationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAssociationUseCase {

    @Autowired
    private LibraryRepository libraryRepository;

    public AssociationResponseDTO execute(AssociationRegisterDTO register) {

        return null;
    }
}
