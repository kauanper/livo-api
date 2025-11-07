package com.livo.book_service.services;

import com.livo.book_service.APIs.GoogleBooksClient;
import com.livo.book_service.dtos.BookResponse;
import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.exceptions.custom.EmptyBookListException;
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
    private final SortingByNewestUseCase sortingByNewestUseCase;

    public List<BookSummaryResponse> execute(String query, String orderBy) {

        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("O termo de busca não pode estar vazio.");
        }

        BookResponse titleResponse = googleBooksClient.searchBooks("intitle:" + query);
        BookResponse authorResponse = googleBooksClient.searchBooks("inauthor:" + query);
        BookResponse subjectResponse = googleBooksClient.searchBooks("subject:" + query);

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

        if(allItems.isEmpty()) {
            throw new EmptyBookListException("Nenhum resultado encontrado para o termo: " + query);
        }

        if(orderBy.equals("newest")){ //(orderBy == relevance) é padrão da API
            allItems = sortingByNewestUseCase.execute(allItems);
        }

        return allItems.stream()
                .map(bookMapper::toSummary)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }
}
