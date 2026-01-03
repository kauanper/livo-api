package com.livo.library_service.search_book.strategies;

import lombok.Getter;

@Getter
public enum SearchType {
    TITLE_LIBRARY("title library"),
    AUTHOR_LIBRARY("author library"),
    GENRE_LIBRARY("genre library"),
    TITLE_SHELVES("title shelves"),
    AUTHOR_SHELVES("author shelves"),
    GENRE_SHELVES("genre shelves"),
    ;

    private final String key;

    SearchType(String key) {
        this.key = key;
    }
}

