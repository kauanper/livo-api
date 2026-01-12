package com.livo.library_service.search_book.strategies;

import com.livo.library_service.library.dtos.association.AssociationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SearchStrategy {
    List<AssociationResponseDTO> search(SearchRequest request);
}
