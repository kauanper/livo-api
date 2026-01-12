package com.livo.library_service.library.services;

import com.livo.library_service.library.BookStatus;
import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.dtos.book_status.BookStatusResponse;
import com.livo.library_service.shared.globalExceptions.custon.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBookStatusUseCase {

    private final LibraryRepository libraryRepository;

    public BookStatusResponse execute(UUID userId, String bookId) {
        UserBookEntity userBook = libraryRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "bookId",
                        "Livro não encontrado na biblioteca do usuário"
                ));

        return new BookStatusResponse(userBook.getBookId(), userBook.getBookStatus());
    }
}

