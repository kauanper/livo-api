package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.custonExceptions.AssociationNotFoundException;
import com.livo.library_service.shelf.bookShelf.BookShelfRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteByIdUseCase {

    private final LibraryRepository libraryRepository;
    private final BookShelfRepository bookShelfRepository;

    @Transactional
    public void execute(Long userBookId) {

        var entity = libraryRepository.findById(userBookId)
                .orElseThrow(() -> new AssociationNotFoundException(userBookId));

        // Remove o livro de todas as prateleiras associadas (regra de negócio:
        // um livro só pode estar na prateleira se estiver na biblioteca)
        bookShelfRepository.deleteAllByBookId(entity.getId());

        libraryRepository.deleteById(entity.getId());
    }
}
