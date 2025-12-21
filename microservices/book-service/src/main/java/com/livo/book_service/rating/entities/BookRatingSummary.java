package com.livo.book_service.rating.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "book_rating_summaries",
       uniqueConstraints = @UniqueConstraint(columnNames = "book_id"),
       indexes = @Index(name = "idx_book_id", columnList = "book_id"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRatingSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "book_id", nullable = false, unique = true, length = 255)
    private String bookId;

    @Column(name = "total_ratings", nullable = false)
    @Builder.Default
    private Integer totalRatings = 0;

    @Column(name = "rating_sum", nullable = false)
    @Builder.Default
    private Integer ratingSum = 0;

    @Column(name = "average_rating", nullable = false)
    @Builder.Default
    private Double averageRating = 0.0;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Calcula e atualiza a média de avaliações baseado no total e na soma.
    // Deve ser chamado sempre que totalRatings ou ratingSum for alterado.
    public void calculateAverage() {
        if (totalRatings != null && totalRatings > 0 && ratingSum != null) {
            this.averageRating = (double) ratingSum / totalRatings;
        } else {
            this.averageRating = 0.0;
        }
    }
}

