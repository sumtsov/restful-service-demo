package com.dsumtsov.book.library.mapper;

import com.dsumtsov.book.library.domain.Author;
import com.dsumtsov.book.library.dto.AuthorDTO;
import com.dsumtsov.book.library.dto.DobDTO;
import com.dsumtsov.book.library.dto.NameDTO;
import com.dsumtsov.book.library.dto.RandomAuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;

import static com.dsumtsov.book.library.util.DateUtils.formatBirthday;

@Mapper(uses = BookMapper.class)
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(source = "author.birthday", target = "birthday", qualifiedByName = "birthdayToString")
    AuthorDTO toDto(Author author);

    @Mapping(target = "books", ignore = true)
    @Mapping(source = "authorDTO.birthday", target = "birthday", qualifiedByName = "birthdayToDate")
    Author toEntity(AuthorDTO authorDTO);

    @Mapping(target = "books", ignore = true)
    @Mapping(source = "dob", target = "birthday", qualifiedByName = "randomAuthorBirthday")
    @Mapping(source = "randomAuthorDTO.name", target = "name", qualifiedByName = "randomAuthorName")
    Author toEntity(RandomAuthorDTO randomAuthorDTO);

    @Named("birthdayToString")
    static String birthdayToString(Date birthday) {
        return formatBirthday(birthday);
    }

    @Named("birthdayToDate")
    static Date birthdayToDate(String birthday) {
        return formatBirthday(birthday);
    }

    @Named("randomAuthorBirthday")
    static Date randomAuthorBirthday(DobDTO dob) {
        return dob.getDate();
    }

    @Named("randomAuthorName")
    static String randomAuthorName(NameDTO name) {
        return String.join(" ",
                name.getTitle(),
                name.getFirst(),
                name.getLast()
        );
    }
}

