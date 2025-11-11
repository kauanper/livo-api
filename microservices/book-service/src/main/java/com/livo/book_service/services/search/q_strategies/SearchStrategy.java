package com.livo.book_service.services.search.q_strategies;

import com.livo.book_service.dtos.BookResponse;

import java.util.List;

public interface SearchStrategy {
    List<BookResponse.BookItem> search(String query, String orderBy);
}
