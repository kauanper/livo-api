package com.livo.library_service.library.dtos.book;

import java.util.List;

public class BookSummaryResponse {

    // ID único do volume (campo "id")
    private String id;

    // Título do livro (campo "volumeInfo.title")
    private String title;

    // Lista de autores (campo "volumeInfo.authors")
    private List<String> authors;

    // Nome da editora (campo "volumeInfo.publisher")
    private String publisher;

    // Data de publicação (campo "volumeInfo.publishedDate")
    private String publishedDate;

    // Número de páginas (campo "volumeInfo.pageCount")
    private Integer pageCount;

    // Avaliação média do livro (campo "volumeInfo.averageRating")
    private Double averageRating;

    // Número de avaliações (campo "volumeInfo.ratingsCount")
    private Integer ratingsCount;

    // URL da imagem da capa (campo "volumeInfo.imageLinks.thumbnail")
    private String thumbnail;

    // Linguagem do livro (campo "volumeInfo.language")
    private String language;

    // Breve descrição do livro (campo "volumeInfo.description")
    private String description;

    // ver se o usuario possui esse livro na sua biblioteca pessoal
    private Boolean personalLibrary;
}
