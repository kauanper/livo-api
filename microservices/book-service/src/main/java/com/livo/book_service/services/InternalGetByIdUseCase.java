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
public class InternalGetByIdUseCase {

    private final GoogleBooksClient googleBooksClient;
    private final BookMapper bookMapper;
    private final LibraryClient libraryClient;

    @Cacheable(value = "bookById", key = "#bookId")
    public BookSummaryResponse execute(String bookId) {

        if (bookId == null || bookId.isBlank()) {
            throw new InvalidRequestException("O ID do livro não pode estar vazio.");
        }

        var bookItem = googleBooksClient.getBookById(bookId);

        if (bookItem == null || bookItem.getVolumeInfo() == null) {
            throw new BookNotFoundException("Livro com ID '" + bookId + "' não foi encontrado.");
        }

        return bookMapper.toSummary(bookItem);
    }
}
