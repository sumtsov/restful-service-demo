package com.dsumtsov.restful.mapper;

import com.dsumtsov.restful.entity.Author;
import com.dsumtsov.restful.dto.AuthorDTO;
import com.dsumtsov.restful.dto.DobDTO;
import com.dsumtsov.restful.dto.NameDTO;
import com.dsumtsov.restful.dto.RandomAuthorDTO;
import com.dsumtsov.restful.util.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;

import static com.dsumtsov.restful.util.DateUtils.formatBirthday;

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
        return DateUtils.formatBirthday(birthday);
    }

    @Named("birthdayToDate")
    static Date birthdayToDate(String birthday) {
        return DateUtils.formatBirthday(birthday);
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

