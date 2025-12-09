package com.livo.library_service.search_book;

import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.library.mappers.AssociationMappers;
import com.livo.library_service.search_book.strategies.SearchRequest;
import com.livo.library_service.search_book.strategies.SearchStrategy;
import com.livo.library_service.shelf.ShelfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TitleShelvesSearchStrategy implements SearchStrategy {

    private final ShelfRepository shelfRepository;
    private final AssociationMappers associationMappers;

    @Override
    public List<AssociationResponseDTO> search(SearchRequest request) {
        UUID userId = request.getUserId();
        UUID shelfId = request.getShelfId();
        String searchTerm = request.getSearchTerm();

        return shelfRepository.searchByShelfAndTitle(shelfId, userId, searchTerm)
                .stream()
                .map(associationMappers::toResponseDTO)
                .toList();
    }
}
