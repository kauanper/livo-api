package com.livo.library_service.reeding_register.services;

import com.livo.library_service.library.validation.LibraryValidationService;
import com.livo.library_service.reeding_register.ReadingLog;
import com.livo.library_service.reeding_register.ReadingLogRepository;
import com.livo.library_service.reeding_register.dtos.ReadingLogRegisterDTO;
import com.livo.library_service.reeding_register.dtos.ReadingLogResponseDTO;
import com.livo.library_service.reeding_register.mappers.ReadingLogMapper;
import com.livo.library_service.reeding_register.validation.ReadingLogValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReadingLogService {
    @Autowired
    private ReadingLogRepository readingLogRepository;

    @Autowired
    private ReadingLogValidationService readingLogValidator;

    @Autowired
    private LibraryValidationService libraryValidator;

    @Autowired
    private ReadingLogMapper readingLogMapper;

    public ReadingLogResponseDTO save(ReadingLogRegisterDTO dto, UUID userId) {
        readingLogValidator.validateToAddReedingLog(dto, userId);
        ReadingLog entity = readingLogMapper.toEntity(dto);
        return readingLogMapper.toDto(readingLogRepository.save(entity));
    }

    public List<ReadingLogResponseDTO> findAllByLibraryBookId(Long libraryBookId, UUID userId) {
        libraryValidator.validateLibraryBookBelongsToUserAndGet(userId, libraryBookId);
        List<ReadingLog> readingLogs = readingLogRepository.findAllByUserBookIdOrderByTimeDesc(libraryBookId);
        return readingLogs.stream().map(log -> readingLogMapper.toDto(log)).toList();
    }

    public ReadingLogResponseDTO findByReadingLogId(Long readingLogId, UUID userId) {
        ReadingLog log = readingLogValidator.validateReadingLogBelongsToUserAndGet(readingLogId, userId);
        return readingLogMapper.toDto(log);
    }

    public ReadingLogResponseDTO update(ReadingLogRegisterDTO dto, Long readingLogId, UUID userId) {
        readingLogValidator.validateToUpdateReadingLogAndGet(dto, readingLogId, userId);
        ReadingLog log = readingLogMapper.toEntity(dto, readingLogId);
        log = readingLogRepository.save(log);
        return readingLogMapper.toDto(log);
    }

    public void delete(Long readingLogId, UUID userId) {
        ReadingLog log = readingLogValidator.validateReadingLogBelongsToUserAndGet(readingLogId, userId);
        readingLogRepository.delete(log);
    }
}
