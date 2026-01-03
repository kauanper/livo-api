package com.livo.library_service.search_book.strategies;

import com.livo.library_service.search_book.TitleLibrarySearchStrategy;
import com.livo.library_service.search_book.TitleShelvesSearchStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SearchStrategySelector {

    private final Map<SearchType, SearchStrategy> registry = new HashMap<>();

    public SearchStrategySelector(
            TitleLibrarySearchStrategy titleLibrarySearchStrategy,
            TitleShelvesSearchStrategy titleShelvesSearchStrategy
    ) {
        registry.put(SearchType.TITLE_LIBRARY, titleLibrarySearchStrategy);
        registry.put(SearchType.TITLE_SHELVES, titleShelvesSearchStrategy);
    }

    public SearchStrategy get(SearchType type) {
        SearchStrategy strategy = registry.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException("SearchStrategy not registered for type: " + type);
        }

        return strategy;
    }
}

