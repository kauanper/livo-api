package com.livo.user_service.utils.dtos;

public record BookCountResponse(
        int wantToRead,
        int reading,
        int read,
        int abandoned,
        int total
) {
}
