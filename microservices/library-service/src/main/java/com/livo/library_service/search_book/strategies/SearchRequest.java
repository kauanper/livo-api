package com.livo.library_service.search_book.strategies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SearchRequest {

    private UUID userId;
    private String searchTerm;

    // opcional — usado apenas por estratégias de Shelf
    private UUID shelfId;

    private SearchType type;

    // pode acrescentar: status, categoria, paginação, tags...
}

