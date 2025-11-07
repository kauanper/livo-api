package com.livo.book_service.controllers;

import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.services.GetBookByIdUseCase;
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

    @Autowired
    GetBookByIdUseCase getBookByIdUseCase;

    @GetMapping("/search")
    public ResponseEntity<List<BookSummaryResponse>> searchBooks(
            @RequestParam String query) {

        List<BookSummaryResponse> response = searchBooksCombinedUseCase.execute(query);
        return ResponseEntity.ok(response);


        //return ResponseEntity.ok(null); //depuração
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookSummaryResponse> getBookById(@PathVariable String id) {
        BookSummaryResponse book = getBookByIdUseCase.execute(id);
        return ResponseEntity.ok(book);
    }

}
