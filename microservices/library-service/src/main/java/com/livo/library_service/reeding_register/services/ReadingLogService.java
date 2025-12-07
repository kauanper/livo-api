package com.livo.library_service.reeding_register.services;

import com.livo.library_service.reeding_register.ReadingLog;
import com.livo.library_service.reeding_register.ReadingLogRepository;
import com.livo.library_service.reeding_register.dtos.ReadingLogRegisterDTO;
import com.livo.library_service.reeding_register.dtos.ReadingLogResponseDTO;
import com.livo.library_service.reeding_register.mappers.ReadingLogMapper;
import com.livo.library_service.reeding_register.validation.ReedingRegisterValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReadingLogService {
    @Autowired
    private ReadingLogRepository readingLogRepository;

    @Autowired
    private ReedingRegisterValidationService reedingRegisterValidator;

    @Autowired
    private ReadingLogMapper readingLogMapper;

    public ReadingLogResponseDTO save(ReadingLogRegisterDTO dto, UUID userId) {
        reedingRegisterValidator.validateToAddReedingRegister(dto, userId);
        ReadingLog entity = readingLogMapper.toEntity(dto);
        return readingLogMapper.toDto(readingLogRepository.save(entity));
    }
}
