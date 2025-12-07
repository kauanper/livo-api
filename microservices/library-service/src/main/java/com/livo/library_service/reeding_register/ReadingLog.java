package com.livo.library_service.reeding_register;

import com.livo.library_service.library.UserBookEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ReadingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_book_id", nullable = false)
    private UserBookEntity userBook;

    private String title;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime time; //valor preenchido na criação e que nunca mais poderá ser alterado.

    @Column(columnDefinition = "text")
    private String text;

    @Column(nullable = false)
    private Integer pagesRead;
}
