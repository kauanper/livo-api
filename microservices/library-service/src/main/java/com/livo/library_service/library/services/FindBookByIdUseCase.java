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

    public BookSummaryResponse execute(String bookId) {
        return bookClient.getBookByIdInternal(bookId);
    }
}
