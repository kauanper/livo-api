package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteByIdUseCase {

    private final LibraryRepository libraryRepository;

    public void execute(Long userBookId) {


        libraryRepository.deleteById(userBookId);
    }
}
