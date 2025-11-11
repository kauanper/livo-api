package com.livo.book_service.APIs;

import com.livo.book_service.dtos.BookResponse;
import com.livo.book_service.exceptions.custom.BookNotFoundException;
import com.livo.book_service.exceptions.custom.ExternalApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class GoogleBooksClient {

    private final RestTemplate restTemplate;

    private static final String API_KEY = "AIzaSyAUXlkrJPjReOwiVzMz_6weuWHM3wxsRgY";

    public BookResponse searchBooks(String query, String orderBy) {
        try {
            URI uri = UriComponentsBuilder
                    .fromHttpUrl("https://www.googleapis.com/books/v1/volumes")
                    .queryParam("q", query)
                    .queryParam("orderBy", orderBy)
                    .queryParam("key", API_KEY)
                    .build()
                    .encode()
                    .toUri();

            return restTemplate.getForObject(uri, BookResponse.class);

        } catch (RestClientException ex) {
            throw new ExternalApiException("Erro ao consultar Google Books: " + ex.getMessage());
        }
    }

    public BookResponse.BookItem getBookById(String volumeId) {
        try {
            URI uri = UriComponentsBuilder
                    .fromHttpUrl("https://www.googleapis.com/books/v1/volumes/" + volumeId)
                    .queryParam("key", API_KEY)
                    .build()
                    .encode()
                    .toUri();

            return restTemplate.getForObject(uri, BookResponse.BookItem.class);

        } catch (HttpClientErrorException.NotFound ex) {
            throw new BookNotFoundException("Livro com ID '" + volumeId + "' não foi encontrado.");
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new BookNotFoundException("O ID '" + volumeId + "' é inválido.");
        } catch (RestClientException ex) {
            throw new ExternalApiException("Erro ao consultar Google Books: " + ex.getMessage());
        }
    }
}
