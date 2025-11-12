package com.livo.book_service.services.search.q_strategies;

import com.livo.book_service.APIs.GoogleBooksClient;
import com.livo.book_service.dtos.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("subject")
@RequiredArgsConstructor
public class SearchBySubjectStrategy implements SearchStrategy {

    private final GoogleBooksClient google;

    @Override
    public List<BookResponse.BookItem> search(String query, String orderBy) {
        BookResponse response = google.searchBooks("subject:" + query, orderBy);
        return response.getItems() != null ? response.getItems() : List.of();
    }
}

