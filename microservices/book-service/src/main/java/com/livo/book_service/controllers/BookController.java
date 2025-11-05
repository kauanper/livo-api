package com.livo.book_service.controllers;

import com.livo.book_service.dtos.BookResponse;
import com.livo.book_service.services.SearchBooksCombinedUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final SearchBooksCombinedUseCase searchBooksCombinedUseCase;

    public BookController(SearchBooksCombinedUseCase searchBooksCombinedUseCase) {
        this.searchBooksCombinedUseCase = searchBooksCombinedUseCase;
    }

    @GetMapping("/search")
    public ResponseEntity<BookResponse> searchBooks(@RequestParam String query) {
        BookResponse response = searchBooksCombinedUseCase.execute(query);
        return ResponseEntity.ok(response);
    }
}
