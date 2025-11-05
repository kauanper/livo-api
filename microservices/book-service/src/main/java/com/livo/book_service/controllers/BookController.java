package com.livo.book_service.controllers;

import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.services.SearchBooksCombinedUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final SearchBooksCombinedUseCase searchBooksCombinedUseCase;

    public BookController(SearchBooksCombinedUseCase searchBooksCombinedUseCase) {
        this.searchBooksCombinedUseCase = searchBooksCombinedUseCase;
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookSummaryResponse>> searchBooks(@RequestParam String query) {
        List<BookSummaryResponse> response = searchBooksCombinedUseCase.execute(query);
        return ResponseEntity.ok(response);
    }
}
