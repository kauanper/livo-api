package com.livo.library_service.library.dtos;

import com.livo.library_service.library.BookStatus;
import jakarta.validation.Valid;

public record BookStatusPatchDTO (
        @Valid
        BookStatus bookStatus
){}
