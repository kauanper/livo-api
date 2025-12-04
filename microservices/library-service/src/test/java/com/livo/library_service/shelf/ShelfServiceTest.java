package com.livo.library_service.shelf;

import com.livo.library_service.shared.clients.BookClient;
import com.livo.library_service.shared.dtos.book.BookSummaryResponse;
import com.livo.library_service.shelf.entity.Shelf;
import com.livo.library_service.shelf.entity.dtos.ShelfDto;
import com.livo.library_service.shelf.entity.dtos.ShelfPostDto;
import com.livo.library_service.shelf.mappers.ShelfMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShelfServiceTest {

    @Mock
    private ShelfRepository shelfRepository;

    @Mock
    private ShelfMapper shelfMapper;

    @Mock
    private ShelfValidate shelfValidate;

    @InjectMocks
    private ShelfService shelfService;

    private UUID userId;
    private UUID shelfId;
    private Shelf shelf;
    private ShelfDto shelfDto;
    private ShelfPostDto shelfPostDto;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        shelfId = UUID.randomUUID();
        shelf = new Shelf();
        shelf.setId(shelfId);
        shelf.setUserId(userId);
        shelf.setName("My Shelf");
        shelf.setBookShelves(new ArrayList<>());

        shelfDto = new ShelfDto(shelfId, "My Shelf", 0, new ArrayList<>());
        shelfPostDto = new ShelfPostDto("My Shelf", "Description", new ArrayList<>());
    }

    @Test
    void save_ShouldReturnShelfDto_WhenValid() {
        when(shelfMapper.toEntity(any(ShelfPostDto.class))).thenReturn(shelf);
        when(shelfRepository.save(any(Shelf.class))).thenReturn(shelf);
        when(shelfMapper.toDto(any(Shelf.class))).thenReturn(shelfDto);

        ShelfDto result = shelfService.save(shelfPostDto, userId);

        assertNotNull(result);
        assertEquals(shelfDto.id(), result.id());
        verify(shelfValidate).validateBooksExist(any());
        verify(shelfRepository).save(any(Shelf.class));
    }

    @Test
    void findAll_ShouldReturnListOfShelves() {
        when(shelfRepository.findByUserId(userId)).thenReturn(List.of(shelf));
        when(shelfMapper.toDtoList(anyList())).thenReturn(List.of(shelfDto));

        List<ShelfDto> result = shelfService.findAll(userId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findById_ShouldReturnShelf_WhenExists() {
        when(shelfValidate.validateShelfOwnership(shelfId, userId)).thenReturn(shelf);
        when(shelfMapper.toDto(shelf)).thenReturn(shelfDto);

        ShelfDto result = shelfService.findById(shelfId, userId);

        assertNotNull(result);
        assertEquals(shelfId, result.id());
    }

    @Test
    void update_ShouldReturnUpdatedShelf() {
        when(shelfValidate.validateShelfOwnership(shelfId, userId)).thenReturn(shelf);
        when(shelfRepository.save(any(Shelf.class))).thenReturn(shelf);
        when(shelfMapper.toDto(any(Shelf.class))).thenReturn(shelfDto);

        ShelfDto result = shelfService.update(shelfId, shelfPostDto, userId);

        assertNotNull(result);
        verify(shelfRepository).save(any(Shelf.class));
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        when(shelfValidate.validateShelfOwnership(shelfId, userId)).thenReturn(shelf);

        shelfService.delete(shelfId, userId);

        verify(shelfRepository).delete(shelf);
    }
}
