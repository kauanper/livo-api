package com.livo.library_service.shared.dtos.book;

import java.util.List;

public record BookSummaryResponse(
        String id,
        String title,
        List<String> authors,
        String publisher,
        String publishedDate,
        Integer pageCount,
        Double averageRating,
        Integer ratingsCount,
        String thumbnail,
        String language,
        String description,
        Boolean personalLibrary,
        LibraryRegistration libraryRegistration
) {}
