package com.livo.book_service.services;

import com.livo.book_service.APIs.GoogleBooksClient;
import com.livo.book_service.dtos.BookResponse;
import com.livo.book_service.dtos.BookSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchBooksCombinedUseCase {

    private final GoogleBooksClient googleBooksClient;

    public List<BookSummaryResponse> execute(String query) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("O termo de busca não pode estar vazio.");
        }

        // Chamadas separadas (título, autor, assunto)
        BookResponse titleResponse = googleBooksClient.searchBooks("intitle:" + query);
        BookResponse authorResponse = googleBooksClient.searchBooks("inauthor:" + query);
        BookResponse subjectResponse = googleBooksClient.searchBooks("subject:" + query);

        Set<String> seenIds = new HashSet<>();
        List<BookResponse.BookItem> allItems = new ArrayList<>();

        for (BookResponse response : List.of(titleResponse, authorResponse, subjectResponse)) {
            if (response.getItems() != null) {
                for (BookResponse.BookItem item : response.getItems()) {
                    if (seenIds.add(item.getId())) {
                        allItems.add(item);
                    }
                }
            }
        }

        // Converte para formato simplificado
        return allItems.stream()
                .map(item -> {
                    var info = item.getVolumeInfo();
                    String thumbnail = (info.getImageLinks() != null)
                            ? info.getImageLinks().getThumbnail()
                            : null;

                    return new BookSummaryResponse(
                            item.getId(),                      // id
                            info.getTitle(),                   // title
                            info.getAuthors(),                 // authors
                            info.getPublisher(),               // publisher
                            info.getPublishedDate(),           // publishedDate
                            info.getPageCount(),               // pageCount
                            info.getAverageRating(),           // averageRating
                            info.getRatingsCount(),            // ratingsCount
                            thumbnail                          // thumbnail
                    );
                })
                .collect(Collectors.toList());

    }
}
