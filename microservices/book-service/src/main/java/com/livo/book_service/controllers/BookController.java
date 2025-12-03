package com.livo.book_service.controllers;

import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.services.FindBookByIdUseCase;
import com.livo.book_service.services.GetBookByIdUseCase;
import com.livo.book_service.services.InternalGetByIdUseCase;
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
    private GetBookByIdUseCase getBookByIdUseCase;

    @Autowired
    private FindBookByIdUseCase findBookByIdUseCase;

    @Autowired
    private InternalGetByIdUseCase internalGetByIdUseCase;

    @GetMapping("/search")
    public ResponseEntity<List<BookSummaryResponse>> searchBooks(
            @CurrentUser UUID currentUser,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String query,
            @RequestParam String type,
            @RequestParam String orderBy) {
        String token = authorizationHeader.replace("Bearer ", "");

        List<BookSummaryResponse> responses = searchBooksUseCase.execute(query, type, orderBy);
        responses = findBookByIdUseCase.execute(responses, currentUser);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookSummaryResponse> getBookById(
            @CurrentUser UUID currentUser,
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String id) {

        BookSummaryResponse book = getBookByIdUseCase.execute(id, currentUser);
        return ResponseEntity.ok(book);
    }

    //----------------------INTERNAL
    @GetMapping("/internal/{id}")
    public ResponseEntity<BookSummaryResponse> getBookByIdInternal(
            @PathVariable String id) {

        BookSummaryResponse book = internalGetByIdUseCase.execute(id);
        return ResponseEntity.ok(book);
    }

}
