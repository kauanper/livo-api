package com.livo.book_service.services;

import com.livo.book_service.APIs.GoogleBooksClient;
import com.livo.book_service.APIs.LibraryClient;
import com.livo.book_service.dtos.BookIdResponse;
import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.exceptions.custom.BookNotFoundException;
import com.livo.book_service.exceptions.custom.InvalidRequestException;
import com.livo.book_service.mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetBookByIdUseCase {

    private final GoogleBooksClient googleBooksClient;
    private final BookMapper bookMapper;
    private final LibraryClient libraryClient;

    public BookSummaryResponse execute(String bookId, UUID userId) {

        if (bookId == null || bookId.isBlank()) {
            throw new InvalidRequestException("O ID do livro não pode estar vazio.");
        }

        var bookItem = googleBooksClient.getBookById(bookId);

        if (bookItem == null || bookItem.getVolumeInfo() == null) {
            throw new BookNotFoundException("Livro com ID '" + bookId + "' não foi encontrado.");
        }

        //buscar IDs da biblioteca do usuário
        List<BookIdResponse> booksIdResponse = libraryClient.getBooksId(userId);

        //transformar em Set para busca eficiente
        Set<String> userBookIds = booksIdResponse.stream()
                .map(BookIdResponse::getBookId)
                .collect(Collectors.toSet());

        //mapear GoogleBook → BookSummaryResponse
        BookSummaryResponse response = bookMapper.toSummary(bookItem);

        //verificar se o usuário possui esse livro
        boolean hasBook = userBookIds.contains(bookId);
        response.setPersonalLibrary(hasBook);

        return response;
    }
}
