package com.livo.book_service.services;

import com.livo.book_service.dtos.BookResponse;
import com.livo.book_service.dtos.BookSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SortingByNewestUseCase {
    public List<BookResponse> execute(List<BookResponse> bookResponses){



        return bookResponses;
    }
}
