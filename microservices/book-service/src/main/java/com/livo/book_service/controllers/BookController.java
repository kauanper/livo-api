package com.livo.book_service.controllers;

import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.services.SearchBooksCombinedUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private SearchBooksCombinedUseCase searchBooksCombinedUseCase;

    @GetMapping("/search")
    public ResponseEntity<List<BookSummaryResponse>> searchBooks(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "relevance") String orderBy) {

        List<BookSummaryResponse> response = searchBooksCombinedUseCase.execute(query, orderBy);

        return ResponseEntity.ok(response);
    }

}
