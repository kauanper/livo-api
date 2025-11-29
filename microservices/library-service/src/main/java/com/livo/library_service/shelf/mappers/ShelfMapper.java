package com.livo.library_service.shelf.mappers;

import com.livo.library_service.shelf.bookShelf.BookShelf;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfDto;
import com.livo.library_service.shelf.entity.Shelf;
import com.livo.library_service.shelf.entity.dtos.ShelfDto;
import com.livo.library_service.shelf.entity.dtos.ShelfPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShelfMapper {

    @Mapping(target = "quantity", expression = "java(shelf.getBookShelves() != null ? shelf.getBookShelves().size() : 0)")
    @Mapping(target = "bookShelfDto", source = "bookShelves")
    ShelfDto toDto(Shelf shelf);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookShelves", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Shelf toEntity(ShelfPostDto dto);

    List<ShelfDto> toDtoList(List<Shelf> shelves);

    @Mapping(target = "bookId", source = "book_id")
    @Mapping(target = "addedAt", source = "added_at")
    BookShelfDto toBookShelfDto(BookShelf bookShelf);
}
