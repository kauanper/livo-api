package com.livo.book_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSummaryResponse {
    private String title;
    private List<String> authors;
    private String publisher;
    private String thumbnail;
}
