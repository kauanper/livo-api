package com.livo.library_service.library;

import com.livo.library_service.library.dtos.AssociationRegisterDTO;
import com.livo.library_service.library.dtos.AssociationResponseDTO;
import com.livo.library_service.library.services.CreateAssociationUseCase;
import com.livo.library_service.library.services.DeleteByIdUseCase;
import com.livo.library_service.library.services.ListLibraryBooksUseCase;
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
    @Autowired
    private ListLibraryBooksUseCase listLibraryBooksUseCase;

    @PostMapping
    public ResponseEntity<AssociationResponseDTO> save(@RequestBody @Valid AssociationRegisterDTO dto){
        AssociationResponseDTO response = createAssociationUseCase.execute(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/book/{userBookId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long userBookId) {
        deleteByIdUseCase.execute(userBookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssociationResponseDTO>> getLibraryByUserId(@PathVariable UUID userId) {
        List<AssociationResponseDTO> books = listLibraryBooksUseCase.execute(userId);
        return ResponseEntity.ok(books);
    }

}
