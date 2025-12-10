package com.livo.book_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookIdResponse {
    String bookId;
    BookStatus bookStatus;
    Long userBookId;
    Integer readingProgress;
    Integer personalRatting;
    Integer generalRatting;
}

