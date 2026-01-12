package com.livo.user_service.utils.clients;

import com.livo.user_service.utils.dtos.BookCountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "library-service", url = "${library-service.url}", configuration = LibraryClientConfig.class
// fallback = LibraryClientFallback.class // Lembrar de descomentar quando
// configurar circuit breaker
)
public interface LibraryClient {

    @GetMapping("/library/internal/book-count/{userId}")
    BookCountResponse getBookCount(
            @PathVariable("userId") UUID userId);
}
