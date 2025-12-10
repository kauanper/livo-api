package com.livo.book_service.services;

import com.livo.book_service.APIs.LibraryClient;
import com.livo.book_service.dtos.BookIdResponse;
import com.livo.book_service.dtos.BookSummaryResponse;
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

    public List<BookSummaryResponse> execute(List<BookSummaryResponse> responses, UUID userId) {

        List<BookIdResponse> booksIdResponse = libraryClient.getBooksId(userId);

        var userBooksMap = booksIdResponse.stream()
                .collect(Collectors.toMap(BookIdResponse::getBookId, b -> b));

        responses.forEach(book -> {

            BookIdResponse userBookData = userBooksMap.get(book.getId());

            boolean hasBook = userBookData != null;
            book.setPersonalLibrary(hasBook);

            if (hasBook) {
                book.setBookIdResponse(userBookData);
            }
        });

        return responses;
    }
}

