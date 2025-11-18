package com.livo.library_service.library.controllers;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.dtos.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.AssociationResponseDTO;
import com.livo.library_service.library.services.CreateAssociationUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private CreateAssociationUseCase createAssociationUseCase;

    @PostMapping
    public ResponseEntity<AssociationResponseDTO> saveCategoria(@RequestBody @Valid AssociationRegisterDTO dto){
        AssociationResponseDTO response = createAssociationUseCase.execute(dto);
        return ResponseEntity.ok(null);
    }
}
