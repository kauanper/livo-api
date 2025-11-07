package com.livo.book_service.services;

import com.livo.book_service.dtos.BookResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SortingByNewestUseCase {

    public List<BookResponse.BookItem> execute(List<BookResponse.BookItem> items) {
        if (items == null || items.size() <= 1) {
            return items;
        }
        return mergeSort(items);
    }

    private List<BookResponse.BookItem> mergeSort(List<BookResponse.BookItem> items) { //n sempre <=30
        if (items.size() <= 1) return items;

        int mid = items.size() / 2;

        List<BookResponse.BookItem> left = mergeSort(items.subList(0, mid));
        List<BookResponse.BookItem> right = mergeSort(items.subList(mid, items.size()));

        return merge(left, right);
    }

    private List<BookResponse.BookItem> merge(List<BookResponse.BookItem> left, List<BookResponse.BookItem> right) {
        List<BookResponse.BookItem> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            LocalDate dateLeft = parseDate(left.get(i).getVolumeInfo().getPublishedDate());
            LocalDate dateRight = parseDate(right.get(j).getVolumeInfo().getPublishedDate());

            if (dateLeft.isAfter(dateRight)) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }

    private LocalDate parseDate(String date) {//a poha da API coloca data como String
        if (date == null || date.isBlank()) {
            return LocalDate.MIN;
        }

        try {
            if (date.matches("\\d{4}")) {
                return LocalDate.of(Integer.parseInt(date), 1, 1);
            }
            if (date.matches("\\d{4}-\\d{2}")) {
                String[] parts = date.split("-");
                return LocalDate.of(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        1
                );
            }
            return LocalDate.parse(date);

        } catch (Exception e) {
            return LocalDate.MIN;
        }
    }
}