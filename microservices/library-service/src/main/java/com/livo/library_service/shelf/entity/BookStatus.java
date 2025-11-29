package com.livo.library_service.shelf.entity;

import lombok.Getter;

@Getter
public enum BookStatus {
    LENDO("lendo"),
    LIDO("lido"),
    QUERO_LER("quero ler"),
    ABANDONADO("abandonado");

    private final String value;

    BookStatus(String value) {
        this.value = value;
    }
}
