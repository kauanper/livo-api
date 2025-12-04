package com.livo.library_service.library.services;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.library.custonExceptions.EmptyPersonalLibraryException;
import com.livo.library_service.shared.clients.BookClient;
import com.livo.library_service.shared.clients.UserClient;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import com.livo.library_service.shared.globalExceptions.custon.UserNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindBookByIdUseCase {

    private final UserClient userClient;
    private final BookClient bookClient;
    private final LibraryRepository libraryRepository;

    public BookSummaryResponse execute(UUID userId, Long id, String bookId) {

        try {
            userClient.getById(userId);
        } catch (FeignException.NotFound e) {
            throw new UserNotFoundException(userId);
        }

        UserBookEntity book = libraryRepository
                .findById(id)
                .orElseThrow(EmptyPersonalLibraryException::new);

        return bookClient.getBookByIdInternal(bookId);
    }
}
