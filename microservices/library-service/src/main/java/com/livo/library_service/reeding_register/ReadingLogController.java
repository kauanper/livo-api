package com.livo.library_service.reeding_register;

import com.livo.library_service.reeding_register.dtos.ReadingLogRegisterDTO;
import com.livo.library_service.reeding_register.dtos.ReadingLogResponseDTO;
import com.livo.library_service.reeding_register.services.ReadingLogService;
import com.livo.library_service.shared.notations.CurrentUser;
import feign.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/library/reading-logs")
public class ReadingLogController {
    @Autowired
    private ReadingLogService readingLogService;

    @PostMapping
    public ResponseEntity<ReadingLogResponseDTO> saveReadingRegister(@RequestBody @Valid ReadingLogRegisterDTO dto,
                                                                     @CurrentUser UUID userId){
        var response = readingLogService.save(dto, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReadingLogResponseDTO>> getReadingLogs(@RequestParam(required = true) Long libraryBookId,
                                                                      @CurrentUser UUID userId){
        List<ReadingLogResponseDTO> response = readingLogService.findAllByLibraryBookId(libraryBookId, userId);
        return ResponseEntity.ok(response);
    }
}
