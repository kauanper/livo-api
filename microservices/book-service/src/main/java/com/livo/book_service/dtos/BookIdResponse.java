package com.livo.book_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookIdResponse {
    private String bookId;
    private Long userBookId;
    private BookStatus bookStatus;
    private BigDecimal progress;
    private Integer ratingPersonal;
    private Double averageRatingOnTheApp;
}

