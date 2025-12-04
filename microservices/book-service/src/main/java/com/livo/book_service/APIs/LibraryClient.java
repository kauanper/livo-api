package com.livo.book_service.APIs;

import com.livo.book_service.dtos.BookIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "library-service")
public interface LibraryClient {

    @GetMapping("/library/internal/booksId/{userId}")
    List<BookIdResponse> getBooksId(
            @PathVariable("userId") UUID userId
    );
}
