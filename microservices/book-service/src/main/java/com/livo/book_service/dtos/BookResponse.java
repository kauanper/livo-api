package com.livo.book_service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BookResponse {
    private List<BookItem> items;

    @Data
    public static class BookItem {
        private String id;
        private VolumeInfo volumeInfo;
    }

    @Data
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private String publishedDate;
        private String description;
        private ImageLinks imageLinks;
    }

    @Data
    public static class ImageLinks {
        private String thumbnail;
    }
}

