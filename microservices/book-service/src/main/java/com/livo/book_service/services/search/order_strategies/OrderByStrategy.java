package com.livo.book_service.services.search.order_strategies;

import com.livo.book_service.dtos.BookResponse;

import java.util.List;

public interface OrderByStrategy {
    List<BookResponse.BookItem> sort(List<BookResponse.BookItem> items);
}
