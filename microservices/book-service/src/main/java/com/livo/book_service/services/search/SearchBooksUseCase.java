package com.livo.book_service.services.search;

import com.livo.book_service.dtos.BookResponse;
import com.livo.book_service.dtos.BookSummaryResponse;
import com.livo.book_service.exceptions.custom.EmptyBookListException;
import com.livo.book_service.exceptions.custom.InvalidRequestException;
import com.livo.book_service.mappers.BookMapper;
import com.livo.book_service.services.search.order_strategies.OrderByStrategy;
import com.livo.book_service.services.search.order_strategies.OrderByStrategyFactory;
import com.livo.book_service.services.search.q_strategies.SearchStrategy;
import com.livo.book_service.services.search.q_strategies.SearchStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchBooksUseCase {

    private final SearchStrategyFactory searchStrategyFactory;
    private final OrderByStrategyFactory orderByStrategyFactory;
    private final BookMapper mapper;

    @Cacheable(value = "booksSearch", key = "#query + '-' + #type + '-' + #orderBy")
    public List<BookSummaryResponse> execute(String query, String type, String orderBy) {

        validate(query, type, orderBy);

        //strategy para tipo de busca (q)
        SearchStrategy searchStrategy = searchStrategyFactory.get(type);
        List<BookResponse.BookItem> items = searchStrategy.search(query, orderBy);

        if (items.isEmpty()) {
            throw new EmptyBookListException("Nenhum livro encontrado.");
        }

        //strategy para ordenação
        OrderByStrategy orderStrategy = orderByStrategyFactory.get(orderBy);
        items = orderStrategy.sort(items);

        //logica para avizualizar se o user possui na biblioteca pessoal

        return items.stream()
                .map(mapper::toSummary)
                .toList();
    }

    private void validate(String query, String type, String orderBy) {
        if (query == null || query.isBlank())
            throw new InvalidRequestException("Query inválida.");
        if (type == null || type.isBlank())
            throw new InvalidRequestException("Tipo de busca obrigatório.");
        if (orderBy == null || orderBy.isBlank())
            throw new InvalidRequestException("OrderBy obrigatório.");
    }
}

