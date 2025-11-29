package com.livo.library_service.shelf;

import com.livo.library_service.shared.notations.CurrentUser;
import com.livo.library_service.shelf.entity.dtos.ShelfDto;
import com.livo.library_service.shelf.entity.dtos.ShelfPostDto;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/library/shelfs")
public class ShelfController {

    @Autowired
    private ShelfService shelfService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ShelfDto> register(
            @Valid @RequestBody ShelfPostDto postDto,
            @CurrentUser UUID userId
            ) {
        ShelfDto dto = shelfService.save(postDto, userId);
        URI uri = URI.create("/shelfs/" + dto.id());
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ShelfDto>> getAll(@CurrentUser UUID userId) {
        List<ShelfDto> shelfs = shelfService.findAll(userId);
        return ResponseEntity.ok().body(shelfs);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShelfDto> getbyId(@PathParam("id") UUID id, @CurrentUser UUID userId) {
        ShelfDto shelf = shelfService.findById(id, userId);
        return ResponseEntity.ok().body(shelf);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShelfDto> update(
            @PathParam("id") UUID id,
            @Valid @RequestBody ShelfPostDto postDto,
            @CurrentUser UUID userId
    ) {
        ShelfDto dto = shelfService.update(id, postDto, userId);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathParam("id") UUID id, @CurrentUser UUID userId) {
        shelfService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
