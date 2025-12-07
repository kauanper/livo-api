package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.shared.globalExceptions.custon.ResourceNotFoundException;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindUserBookByIdUseCase {
    private final LibraryRepository libraryRepository;

    public FindUserBookByIdUseCase(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public UserBookEntity execute(Long userBookId){
        Optional<UserBookEntity> userBook = libraryRepository.findById(userBookId);
        if(userBook.isEmpty())
            throw new ResourceNotFoundException("userBook", "id de library não encontrado");
        return userBook.get();
    }
}
