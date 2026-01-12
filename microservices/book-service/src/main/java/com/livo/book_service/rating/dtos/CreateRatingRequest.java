package com.livo.book_service.rating.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRatingRequest {

    @NotNull(message = "A avaliação é obrigatória")
    @Min(value = 1, message = "A avaliação deve ser no mínimo 1")
    @Max(value = 5, message = "A avaliação deve ser no máximo 5")
    private Integer rating;
}

