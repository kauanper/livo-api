package com.livo.book_service.services.search.order_strategies;

import com.livo.book_service.dtos.BookResponse;
import com.livo.book_service.services.SortingByNewestUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("newest")
public class OrderByNewestStrategy implements OrderByStrategy {

    @Autowired
    SortingByNewestUseCase sortingByNewestUseCase;

    @Override
    public List<BookResponse.BookItem> sort(List<BookResponse.BookItem> items) {
        return sortingByNewestUseCase.execute(items); //reaproveitando minha classe
    }
}

