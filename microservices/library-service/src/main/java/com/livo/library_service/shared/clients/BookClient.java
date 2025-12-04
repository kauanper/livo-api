package com.livo.library_service.shared.clients;

import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookClient {

    @GetMapping("/books/internal/{id}")
    BookSummaryResponse getBookByIdInternal(@PathVariable("id") String id);

}
