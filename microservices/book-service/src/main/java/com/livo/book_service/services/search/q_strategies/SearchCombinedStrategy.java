package com.livo.book_service.services.search.q_strategies;

import com.livo.book_service.APIs.GoogleBooksClient;
import com.livo.book_service.dtos.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("combined")
@RequiredArgsConstructor
public class SearchCombinedStrategy implements SearchStrategy {

    private final GoogleBooksClient google;

    @Override
    public List<BookResponse.BookItem> search(String query, String orderBy) {

        BookResponse title = google.searchBooks("intitle:" + query, orderBy);
        BookResponse author = google.searchBooks("inauthor:" + query, orderBy);
        BookResponse subject = google.searchBooks("subject:" + query, orderBy);

        Set<String> ids = new HashSet<>();
        List<BookResponse.BookItem> list = new ArrayList<>();

        for (BookResponse response : List.of(title, author, subject)) {
            if (response.getItems() != null) {
                for (BookResponse.BookItem item : response.getItems()) {
                    if (ids.add(item.getId())) {
                        list.add(item);
                    }
                }
            }
        }

        return list;
    }
}
