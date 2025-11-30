package com.livo.library_service.shelf.mappers;

import com.livo.library_service.shelf.bookShelf.BookShelf;
import com.livo.library_service.shelf.bookShelf.dto.BookShelfDto;
import com.livo.library_service.shelf.entity.Shelf;
import com.livo.library_service.shelf.entity.dtos.ShelfDto;
import com.livo.library_service.shelf.entity.dtos.ShelfPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ShelfMapper {

     @Mapping(target = "quantity", expression = "java(shelf.getBookShelves() != null ? shelf.getBookShelves().size() : 0)")
     @Mapping(target = "bookShelfDto", source = "bookShelves")
     public abstract ShelfDto toDto(Shelf shelf);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "bookShelves", ignore = true)
     @Mapping(target = "userId", ignore = true)
     public abstract Shelf toEntity(ShelfPostDto dto);

//    public Shelf toEntity(ShelfPostDto dto) {
//        if (dto == null) {
//            return null;
//        }
//
//        Shelf shelf = new Shelf();
//
//        shelf.setName(dto.name());
//        shelf.setDescription(dto.description());
//
//        return shelf;
//    }

//    public ShelfDto toDto(Shelf shelf) {
//        if (shelf == null) {
//            return null;
//        }
//
//        List<BookShelfDto> bookShelfDtoList = null;
//        List<BookShelf> list = shelf.getBookShelves();
//        if (list != null) {
//            bookShelfDtoList = new ArrayList<BookShelfDto>(list.size());
//            for (BookShelf bookShelf : list) {
//                bookShelfDtoList.add(toBookShelfDto(bookShelf));
//            }
//        }
//
//        Integer quantity = (shelf.getBookShelves() != null ? shelf.getBookShelves().size() : 0);
//
//        return new ShelfDto(
//                shelf.getId(),
//                shelf.getName(),
//                quantity,
//                bookShelfDtoList);
//    }

    public List<ShelfDto> toDtoList(List<Shelf> shelves) {
        List<ShelfDto> dtos = new ArrayList<>();
        for (Shelf s : shelves)
            dtos.add(toDto(s));

        return dtos;
    }

     @Mapping(target = "bookId", source = "book_id")
     @Mapping(target = "addedAt", source = "added_at")
     abstract BookShelfDto toBookShelfDto(BookShelf bookShelf);

//    public BookShelfDto toBookShelfDto(BookShelf bookShelf) {
//        if (bookShelf == null) {
//            return null;
//        }
//
//        return new BookShelfDto(
//                bookShelf.getId(),
//                bookShelf.getBook_id(),
//                bookShelf.getStatus(),
//                bookShelf.getAdded_at(),
//                bookShelf.getRating());
//    }
}
