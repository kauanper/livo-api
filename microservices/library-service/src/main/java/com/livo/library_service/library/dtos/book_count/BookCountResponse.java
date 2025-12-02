package com.livo.library_service.library.dtos.book_count;

public record BookCountResponse(
        int want_to_read,
        int reading,
        int read,
        int abandoned,
        int total
) {
}
