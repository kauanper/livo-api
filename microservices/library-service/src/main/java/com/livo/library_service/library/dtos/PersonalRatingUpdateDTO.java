package com.livo.library_service.library.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PersonalRatingUpdateDTO(
        @NotNull(message = "A avaliação é obrigatória")
        @Min(value = 1, message = "A avaliação deve ser no mínimo 1")
        @Max(value = 5, message = "A avaliação deve ser no máximo 5")
        Integer personalRating
) {}
