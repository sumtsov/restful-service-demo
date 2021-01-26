package com.dsumtsov.book.library.mapper;

import com.dsumtsov.book.library.domain.Author;
import com.dsumtsov.book.library.dto.BookDTO;
import com.dsumtsov.book.library.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "author", target = "authorName", qualifiedByName = "authorToName")
    BookDTO toDto(Book book);

    @Mapping(target = "author", ignore = true)
    Book toEntity(BookDTO bookDTO);

    @Named("authorToName")
    static String authorToName(Author author) {
        return author.getName();
    }
}
