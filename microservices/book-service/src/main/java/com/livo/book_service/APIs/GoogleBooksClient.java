package com.livo.book_service.APIs;

import com.livo.book_service.dtos.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class GoogleBooksClient {

    private final RestTemplate restTemplate;

    public BookResponse searchBooks(String query) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://www.googleapis.com/books/v1/volumes")
                .queryParam("q", query)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(uri, BookResponse.class);
    }
}
