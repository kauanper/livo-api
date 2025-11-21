package com.livo.library_service.shared.clients;

import com.livo.library_service.library.dtos.book.BookSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "book-service",
        url = "http://localhost:8084"
)
public interface BookClient {

    @GetMapping("/books/{id}")
    BookSummaryResponse getBookById(@PathVariable("id") String id);

}
