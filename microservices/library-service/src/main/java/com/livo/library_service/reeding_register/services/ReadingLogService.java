package com.livo.library_service.reeding_register.services;

import com.livo.library_service.library.validation.LibraryValidationService;
import com.livo.library_service.reeding_register.ReadingLog;
import com.livo.library_service.reeding_register.ReadingLogRepository;
import com.livo.library_service.reeding_register.dtos.ReadingLogRegisterDTO;
import com.livo.library_service.reeding_register.dtos.ReadingLogResponseDTO;
import com.livo.library_service.reeding_register.mappers.ReadingLogMapper;
import com.livo.library_service.reeding_register.validation.ReadingLogValidationService;
import com.livo.library_service.shared.globalExceptions.custon.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        readingLogValidator.validateToAddReedingRegister(dto, userId);
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
}
