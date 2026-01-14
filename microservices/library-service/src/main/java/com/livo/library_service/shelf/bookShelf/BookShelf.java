package com.livo.library_service.shelf.bookShelf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.livo.library_service.shelf.entity.BookStatus;
import com.livo.library_service.shelf.entity.Shelf;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "book_shelf")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookShelf {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // aqui é o ID da entidade que guardamos do livro na biblioteca, esse, sim, contem o ID do livro no Google Books
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "google_book_id", nullable = false)
    private String googleBookId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "shelf_id", nullable = false)
    private Shelf shelf;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Column(name = "thumbnail", columnDefinition = "text")
    private String thumbnail;

    @Column(name = "title")
    private String title;

    private LocalDateTime added_at;

    private Float rating;
}
