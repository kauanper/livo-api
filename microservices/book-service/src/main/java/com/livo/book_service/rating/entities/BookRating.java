package com.livo.book_service.rating.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "book_ratings", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}),
       indexes = @Index(name = "idx_user_book", columnList = "user_id, book_id"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRating {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "book_id", nullable = false, length = 255)
    private String bookId;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

