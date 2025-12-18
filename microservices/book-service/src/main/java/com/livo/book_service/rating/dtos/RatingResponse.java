package com.livo.book_service.rating.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {

    private UUID id;
    private UUID userId;
    private String bookId;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

