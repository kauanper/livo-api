package com.livo.book_service.services.search.order_strategies;

import com.livo.book_service.exceptions.custom.OrderByInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderByStrategyFactory {

    private final Map<String, OrderByStrategy> strategies;

    public OrderByStrategy get(String orderBy) {
        OrderByStrategy s = strategies.get(orderBy);
        if (s == null) {
            throw new OrderByInvalidException("OrderBy inválido: " + orderBy);
        }
        return s;
    }
}

