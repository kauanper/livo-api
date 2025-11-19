package com.livo.library_service.library.controllers;

import com.livo.library_service.library.dtos.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.AssociationResponseDTO;
import com.livo.library_service.library.services.CreateAssociationUseCase;
import com.livo.library_service.library.services.DeleteByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private CreateAssociationUseCase createAssociationUseCase;
    @Autowired
    private DeleteByIdUseCase deleteByIdUseCase;

    @PostMapping
    public ResponseEntity<AssociationResponseDTO> save(@RequestBody @Valid AssociationRegisterDTO dto){
        AssociationResponseDTO response = createAssociationUseCase.execute(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userBookId}")
    public ResponseEntity<AssociationResponseDTO> deleteById(@PathVariable Long userBookId){
        deleteByIdUseCase.execute(userBookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AssociationResponseDTO> getLibraryByUserId(@PathVariable UUID userId){

        return null;
    }

}
