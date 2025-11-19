package com.livo.library_service.library;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "user_book", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "book_id"})
})
public class UserBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //combinação uniqca
    private UUID userId;
    private String bookId;

    //atributos para não sobre carregar a ApiGoogleBook
    @Column(columnDefinition = "TEXT")
    private String thumbnail;
    private String title;

    //para ajudar na logica interna
    private Integer readingProgress;
    private Integer personalRatting = null;

    //implementar status

}

// representação da biblioteca