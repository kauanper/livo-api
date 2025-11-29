package com.livo.library_service.shelf.entity;

import com.livo.library_service.shelf.bookShelf.BookShelf;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "shelf", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "shelf_id" })
})
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    @Column(name = "user_id")
    private UUID userId;

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookShelf> bookShelves;
}
