package com.livo.library_service.shelf.bookShelf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.shelf.Shelf;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_shelf")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookShelf {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @Column(name = "user_book_id")
    private UserBookEntity userBook;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;
}
