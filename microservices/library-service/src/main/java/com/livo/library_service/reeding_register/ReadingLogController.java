package com.livo.library_service.reeding_register;

import com.livo.library_service.reeding_register.dtos.ReadingLogRegisterDTO;
import com.livo.library_service.reeding_register.dtos.ReadingLogResponseDTO;
import com.livo.library_service.reeding_register.services.ReadingLogService;
import com.livo.library_service.shared.notations.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/library/reading-logs")
public class ReadingLogController {
    @Autowired
    private ReadingLogService readingLogService;

    @PostMapping
    public ResponseEntity<ReadingLogResponseDTO> saveReadingLog(@RequestBody @Valid ReadingLogRegisterDTO dto,
                                                                @CurrentUser UUID userId){
        var response = readingLogService.save(dto, userId);
        URI uri = URI.create("/library/reading-logs/" + response.id());
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReadingLogResponseDTO>> getReadingLogsByLibraryBook(@RequestParam(required = true) Long libraryBookId,
                                                                                   @CurrentUser UUID userId){
        List<ReadingLogResponseDTO> response = readingLogService.findAllByLibraryBookId(libraryBookId, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{readingLogId}")
    public ResponseEntity<ReadingLogResponseDTO> getbyReadingLogById(@PathVariable Long readingLogId, @CurrentUser UUID userId){
        ReadingLogResponseDTO response = readingLogService.findByReadingLogId(readingLogId, userId);
        return ResponseEntity.ok(response);
    }

    //public ResponseEntity<ReadingLogResponseDTO>
}
