package com.livo.library_service.library.dtos.book_count;

public record BookCountResponse(
        int wantToRead,
        int reading,
        int read,
        int abandoned,
        int total
) {
}
