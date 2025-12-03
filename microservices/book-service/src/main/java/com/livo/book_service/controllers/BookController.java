package com.livo.book_service.controllers;

import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.services.GetBookByIdUseCase;
import com.livo.book_service.services.search.SearchBooksUseCase;
import com.livo.book_service.util.notations.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private SearchBooksUseCase searchBooksUseCase;

    @Autowired
    GetBookByIdUseCase getBookByIdUseCase;

    @GetMapping("/search")
    public ResponseEntity<List<BookSummaryResponse>> searchBooks(
            @CurrentUser UUID currentUser,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String query,
            @RequestParam String type,
            @RequestParam String orderBy) {
        String token = authorizationHeader.replace("Bearer ", "");


        System.out.println(" meu id é: " + currentUser);

        return ResponseEntity.ok(searchBooksUseCase.execute(query, type, orderBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookSummaryResponse> getBookById(@PathVariable String id) {
        BookSummaryResponse book = getBookByIdUseCase.execute(id);
        return ResponseEntity.ok(book);
    }

}
