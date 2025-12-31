package com.livo.library_service.shared.clients;

import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", url = "${BOOK_SERVICE_URL:localhost:8084}", configuration = BookClientConfig.class
// fallback = BookClientFallback.class // Lembrar de descomentar quando
// configurar circuit breaker
)
public interface BookClient {

    @GetMapping("/books/internal/{id}")
    BookSummaryResponse getBookByIdInternal(@PathVariable("id") String id);

}
