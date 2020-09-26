package com.dsumtsov.bookinventoryservice.mapper;

import com.dsumtsov.bookinventoryservice.dto.AuthorDTO;
import com.dsumtsov.bookinventoryservice.dto.BookDTO;
import com.dsumtsov.bookinventoryservice.domain.Author;
import com.dsumtsov.bookinventoryservice.domain.Book;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static com.dsumtsov.bookinventoryservice.util.DateUtils.formatBirthday;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorMapperTest {

    @Test
    public void entityToDtoTest() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setNumberOfPages(600);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setNumberOfPages(700);

        Author author = new Author();
        author.setId(1L);
        author.setName("Name Surname Patronymic");
        author.setEmail("test@mail.com");
        author.setBirthday(new Date());

        author.addBook(book1);
        author.addBook(book2);

        author.setBooks(Sets.newHashSet(book1, book2));
        AuthorDTO authorDTO = AuthorMapper.INSTANCE.toDto(author);

        assertEquals(author.getId(), authorDTO.getId());
        assertEquals(author.getName(), authorDTO.getName());
        assertEquals(author.getEmail(), authorDTO.getEmail());
        assertEquals(formatBirthday(author.getBirthday()), authorDTO.getBirthday());

        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setId(1L);
        bookDTO1.setTitle("Book 1");
        bookDTO1.setNumberOfPages(600);
        bookDTO1.setAuthorName("Name Surname Patronymic");

        BookDTO bookDTO2 = new BookDTO();
        bookDTO2.setId(2L);
        bookDTO2.setTitle("Book 2");
        bookDTO2.setNumberOfPages(700);
        bookDTO2.setAuthorName("Name Surname Patronymic");

        assertTrue(authorDTO.getBooks().contains(bookDTO1));
        assertTrue(authorDTO.getBooks().contains(bookDTO2));
    }

    @Test
    public void dtoToEntityTest() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("Name Surname Patronymic");
        authorDTO.setEmail("test@mail.com");
        authorDTO.setBirthday("1859-05-22");

        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setId(1L);
        bookDTO1.setTitle("Book 1");
        bookDTO1.setNumberOfPages(600);
        bookDTO1.setAuthorName("Name Surname Patronymic");

        BookDTO bookDTO2 = new BookDTO();
        bookDTO2.setId(2L);
        bookDTO2.setTitle("Book 2");
        bookDTO2.setNumberOfPages(700);
        bookDTO2.setAuthorName("Name Surname Patronymic");

        authorDTO.setBooks(Sets.newHashSet(bookDTO1, bookDTO2));
        Author author = AuthorMapper.INSTANCE.toEntity(authorDTO);

        assertEquals(authorDTO.getId(), author.getId());
        assertEquals(authorDTO.getName(), author.getName());
        assertEquals(authorDTO.getEmail(), author.getEmail());
        assertEquals(formatBirthday(authorDTO.getBirthday()), author.getBirthday());
        assertTrue(author.getBooks().isEmpty());
    }
}
