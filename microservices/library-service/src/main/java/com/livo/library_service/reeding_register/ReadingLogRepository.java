package com.livo.library_service.reeding_register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingLogRepository extends JpaRepository<ReadingLog, Long> {
    public List<ReadingLog> findAllByUserBookIdOrderByTimeDesc(Long userBookId);

    @Query("SELECT rl FROM ReadingLog rl WHERE rl.id = :readingLogId AND rl.userBook.userId = :userId")
    Optional<ReadingLog> findByReadingLogIdAndUserId(Long readingLogId, UUID userId);

    @Query("SELECT MAX(rl.pagesRead) FROM ReadingLog rl WHERE rl.userBook.id = :userBookId")
    Optional<ReadingLog> findMaxByLibraryBookId(Long userBookId);
}
