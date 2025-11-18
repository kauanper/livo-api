package com.livo.library_service.library.controllers;

import com.livo.library_service.library.dtos.AssociationRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @PostMapping
    public ResponseEntity<AssociationRegisterDTO> saveCategoria(@RequestBody @Valid AssociationRegisterDTO dto){
        AssociationRegisterDTO response;
        return ResponseEntity.ok(null);
    }
}
