package com.livo.library_service.reeding_register;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadingLogRepository extends JpaRepository<ReadingLog, Long> {
    public Optional<ReadingLog> findAllByUserBookId(Long userBookId);
}
