package com.livo.book_service.APIs;

import com.livo.book_service.dtos.BookIdResponse;
import com.livo.book_service.dtos.BookStatusResponse;
import com.livo.book_service.dtos.PersonalRatingUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(
    name = "library-service",
    configuration = com.livo.book_service.config.FeignConfig.class
)
public interface LibraryClient {

    @GetMapping("/library/internal/booksId/{userId}")
    List<BookIdResponse> getBooksId(
            @PathVariable("userId") UUID userId
    );

    @GetMapping("/library/internal/book-status/{userId}/{bookId}")
    BookStatusResponse getBookStatus(
            @PathVariable("userId") UUID userId,
            @PathVariable("bookId") String bookId
    );

    @PutMapping("/library/internal/personal-rating/{userId}/{bookId}")
    void updatePersonalRating(
            @PathVariable("userId") UUID userId,
            @PathVariable("bookId") String bookId,
            @RequestBody PersonalRatingUpdateDTO dto
    );

    @DeleteMapping("/library/internal/personal-rating/{userId}/{bookId}")
    void removePersonalRating(
            @PathVariable("userId") UUID userId,
            @PathVariable("bookId") String bookId
    );
}
