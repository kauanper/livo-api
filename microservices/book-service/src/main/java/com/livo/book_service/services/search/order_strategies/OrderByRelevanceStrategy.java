package com.livo.book_service.services.search.order_strategies;

import com.livo.book_service.dtos.BookResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("relevance")
public class OrderByRelevanceStrategy implements OrderByStrategy {
    @Override
    public List<BookResponse.BookItem> sort(List<BookResponse.BookItem> items) {
        return items; //padrao da api
    }
}

