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
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long book_id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "shelf_id", nullable = false)
    private Shelf shelf;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private LocalDateTime added_at;

    private Float rating;
}
