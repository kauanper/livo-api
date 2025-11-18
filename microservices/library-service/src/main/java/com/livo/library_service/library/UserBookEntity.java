package com.livo.library_service.library;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.UUID;

@Entity
@Table(name = "user_book", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "book_id"})
})
public class UserBookEntity {

    @Id
    private UUID id = UUID.randomUUID();

    private UUID userId;
    private String bookId;
}

