package com.livo.library_service.search_book.strategies;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.library.mappers.AssociationMappers;
import com.livo.library_service.shelf.ShelfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TitleShelvesSearchStrategy implements SearchStrategy {

    private final ShelfRepository shelfRepository;

    @Override
    public List<AssociationResponseDTO> search(UUID userId, String searchTerm) {

        return shelfRepository.searchByTitle(userId, searchTerm)
                .stream()
                .map(AssociationMappers::toResponseDTO)
                .toList();
    }
}
