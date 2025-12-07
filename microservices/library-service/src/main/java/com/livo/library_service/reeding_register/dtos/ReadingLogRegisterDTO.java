package com.livo.library_service.reeding_register.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReadingLogRegisterDTO(
        @NotNull
        Long libraryBookId,
        String title, //adicionar notation nullOrNotBlank
        @NotBlank
        String text,
        LocalDateTime time,
        Integer pagesRead
)
{ }
