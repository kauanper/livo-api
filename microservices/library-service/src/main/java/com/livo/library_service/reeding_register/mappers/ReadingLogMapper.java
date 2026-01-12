package com.livo.library_service.reeding_register.mappers;


import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.UserBookEntity;
import com.livo.library_service.reeding_register.ReadingLog;
import com.livo.library_service.reeding_register.dtos.ReadingLogRegisterDTO;
import com.livo.library_service.reeding_register.dtos.ReadingLogResponseDTO;
import com.livo.library_service.reeding_register.services.CalculateProgressService;
import com.livo.library_service.shared.globalExceptions.custon.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ReadingLogMapper {
    @Autowired
    private CalculateProgressService calculateProgressService;

    @Autowired
    private LibraryRepository libraryRepository;

    public ReadingLog toEntity(ReadingLogRegisterDTO dto){
        UserBookEntity userBook = libraryRepository.findById(dto.libraryBookId()).orElseThrow(() -> new ResourceNotFoundException("libraryBookId"));

        ReadingLog entity = new ReadingLog();
        entity.setTitle(dto.title());
        entity.setText(dto.text());
        entity.setTime(dto.time());
        entity.setUserBook(userBook);
        entity.setPagesRead(dto.pagesRead());
        return entity;
    }

    public ReadingLog toEntity(ReadingLogRegisterDTO dto, Long readingLogId){
        UserBookEntity userBook = libraryRepository.findById(dto.libraryBookId()).orElseThrow(() -> new ResourceNotFoundException("libraryBookId"));

        ReadingLog entity = new ReadingLog();
        entity.setId(readingLogId);
        entity.setTitle(dto.title());
        entity.setText(dto.text());
        entity.setTime(dto.time());
        entity.setUserBook(userBook);
        entity.setPagesRead(dto.pagesRead());
        return entity;
    }

    public ReadingLogResponseDTO toDto(ReadingLog entity){
        BigDecimal percentageRead = calculateProgressService.getReedingProgresByPagesRead(entity.getPagesRead(), entity.getUserBook().getBookId());
        ReadingLogResponseDTO dto = new ReadingLogResponseDTO(
                entity.getId(),
                entity.getUserBook().getId(),
                entity.getTitle(),
                entity.getText(),
                entity.getTime(),
                entity.getPagesRead(),
                percentageRead
        );
        return dto;
    }

}
