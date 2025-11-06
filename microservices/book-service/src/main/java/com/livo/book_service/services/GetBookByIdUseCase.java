package com.livo.book_service.services;

import com.livo.book_service.APIs.GoogleBooksClient;
import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBookByIdUseCase {

    private final GoogleBooksClient googleBooksClient;
    private final BookMapper bookMapper;

    public BookSummaryResponse execute(String bookId) {
        if (bookId == null || bookId.isBlank()) {
            throw new IllegalArgumentException("O ID do livro não pode estar vazio.");
        }

        var bookItem = googleBooksClient.getBookById(bookId);
        return bookMapper.toSummary(bookItem);
    }
}
