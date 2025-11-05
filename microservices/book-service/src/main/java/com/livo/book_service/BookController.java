package com.livo.book_service;

import com.livo.book_service.dtos.BookResponse;
import com.livo.book_service.GoogleBooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final GoogleBooksService googleBooksService;

    public BookController(GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
    }

    // Exemplo: GET /books/search?title=Harry%20Potter
    @GetMapping("/search")
    public ResponseEntity<BookResponse> searchByTitle(@RequestParam String title) {
        if (title == null || title.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        BookResponse response = googleBooksService.searchByTitle(title);
        return ResponseEntity.ok(response);
    }
}
