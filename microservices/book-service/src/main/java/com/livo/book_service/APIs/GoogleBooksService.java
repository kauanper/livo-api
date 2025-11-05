package com.livo.book_service.APIs;

import com.livo.book_service.dtos.BookResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class GoogleBooksService {

    private final RestTemplate restTemplate = new RestTemplate();

    public BookResponse searchByTitle(String title) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://www.googleapis.com/books/v1/volumes")
                .queryParam("q", "intitle:" + title)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(uri, BookResponse.class);
    }
}
