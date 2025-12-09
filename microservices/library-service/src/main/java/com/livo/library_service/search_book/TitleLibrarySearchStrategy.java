package com.livo.library_service.search_book;

import com.livo.library_service.library.LibraryRepository;
import com.livo.library_service.library.dtos.association.AssociationResponseDTO;
import com.livo.library_service.library.mappers.AssociationMappers;
import com.livo.library_service.search_book.strategies.SearchRequest;
import com.livo.library_service.search_book.strategies.SearchStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TitleLibrarySearchStrategy implements SearchStrategy {

    private final LibraryRepository libraryRepository;
    private final AssociationMappers associationMappers;

    @Override
    public List<AssociationResponseDTO> search(SearchRequest request) {
        UUID userId = request.getUserId();
        String searchTerm = request.getSearchTerm();

        return libraryRepository.findAllByUserId(userId)
                .stream()
                .filter(book -> book.getTitle() != null &&
                        book.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .map(associationMappers::toResponseDTO)
                .toList();
    }
}
