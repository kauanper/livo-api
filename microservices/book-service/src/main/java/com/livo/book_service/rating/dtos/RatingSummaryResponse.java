package com.livo.book_service.rating.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingSummaryResponse {

    private String bookId;
    private Integer totalRatings;
    private Double averageRating;
    private LocalDateTime updatedAt;
}

