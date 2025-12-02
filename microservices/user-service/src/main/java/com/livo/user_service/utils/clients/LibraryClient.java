package com.livo.user_service.utils.clients;


import com.livo.user_service.utils.dtos.BookCountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "library-service")
public interface LibraryClient {

    @GetMapping("/library/internal/book-count/{userId}")
    BookCountResponse getBookCount(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("userId") UUID userId
    );
}
