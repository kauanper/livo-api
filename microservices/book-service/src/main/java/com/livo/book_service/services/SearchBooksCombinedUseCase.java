package com.livo.book_service.services;

import com.livo.book_service.APIs.GoogleBooksClient;
import com.livo.book_service.dtos.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SearchBooksCombinedUseCase {

    private final GoogleBooksClient googleBooksClient;

    public BookResponse execute(String query) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("O termo de busca não pode estar vazio.");
        }

        // Chamadas separadas
        BookResponse titleResponse = googleBooksClient.searchBooks("intitle:" + query);
        BookResponse authorResponse = googleBooksClient.searchBooks("inauthor:" + query);
        BookResponse subjectResponse = googleBooksClient.searchBooks("subject:" + query);

        // Combinar resultados sem duplicar IDs
        Set<String> seenIds = new HashSet<>();
        List<BookResponse.BookItem> combinedItems = new ArrayList<>();

        for (BookResponse response : List.of(titleResponse, authorResponse, subjectResponse)) {
            if (response.getItems() != null) {
                for (BookResponse.BookItem item : response.getItems()) {
                    if (seenIds.add(item.getId())) {
                        combinedItems.add(item);
                    }
                }
            }
        }

        BookResponse finalResponse = new BookResponse();
        finalResponse.setItems(combinedItems);
        return finalResponse;
    }
}
