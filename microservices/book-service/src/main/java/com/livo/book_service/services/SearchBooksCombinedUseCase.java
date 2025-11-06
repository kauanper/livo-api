package com.livo.book_service.services;

import com.livo.book_service.APIs.GoogleBooksClient;
import com.livo.book_service.dtos.BookResponse;
import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchBooksCombinedUseCase {

    private final GoogleBooksClient googleBooksClient;
    private final BookMapper bookMapper;

    public List<BookSummaryResponse> execute(String query, String sort) {

        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("O termo de busca não pode estar vazio.");
        }

        BookResponse titleResponse = googleBooksClient.searchBooks("intitle:" + query, sort);
        BookResponse authorResponse = googleBooksClient.searchBooks("inauthor:" + query, sort);
        BookResponse subjectResponse = googleBooksClient.searchBooks("subject:" + query, sort);

        Set<String> seenIds = new HashSet<>();
        List<BookResponse.BookItem> allItems = new ArrayList<>();

        for (BookResponse response : List.of(titleResponse, authorResponse, subjectResponse)) {
            if (response.getItems() != null) {
                for (BookResponse.BookItem item : response.getItems()) {
                    if (seenIds.add(item.getId())) {
                        allItems.add(item);
                    }
                }
            }
        }

        return allItems.stream()
                .map(bookMapper::toSummary)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }
}
