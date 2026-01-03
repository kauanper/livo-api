package com.livo.book_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStatusResponse {
    private String bookId;
    private BookStatus bookStatus;
}

