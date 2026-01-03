package com.livo.book_service.services;

import com.livo.book_service.APIs.LibraryClient;
import com.livo.book_service.dtos.BookIdResponse;
import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.dtos.LibraryRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindBookByIdUseCase {

    private final LibraryClient libraryClient;

    public List<BookSummaryResponse> execute(
            List<BookSummaryResponse> responses,
            UUID userId
    ) {

        List<BookIdResponse> booksIdResponse = libraryClient.getBooksId(userId);

        // Map para acesso rápido por bookId
        var libraryMap = booksIdResponse.stream()
                .collect(Collectors.toMap(
                        BookIdResponse::getBookId,
                        book -> book
                ));

        responses.forEach(book -> {
            BookIdResponse libraryBook = libraryMap.get(book.getId());

            if (libraryBook != null) {
                book.setPersonalLibrary(true);
                book.setLibraryRegistration(
                        new LibraryRegistration(
                                libraryBook.getUserBookId(),
                                libraryBook.getBookStatus(),
                                libraryBook.getProgress(),
                                libraryBook.getRatingPersonal()
                        )
                );
            } else {
                book.setPersonalLibrary(false);
                book.setLibraryRegistration(null);
            }
        });

        return responses;
    }
}

