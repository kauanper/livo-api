package com.livo.library_service.shelf;

import com.livo.library_service.shelf.entity.dtos.ShelfDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shelfs")
public class ShelfController {

    @Autowired
    private ShelfService shelfService;

    @PostMapping()
    public ResponseEntity<ShelfDto> register(
            @Valid @RequestBody ShelfDto shelfDto,

            ) {
        ShelfDto dto = shelfService.save(shelfDto, )
    }
}
