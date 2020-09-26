package com.dsumtsov.bookinventoryservice.mapper;

import com.dsumtsov.bookinventoryservice.dto.BookDTO;
import com.dsumtsov.bookinventoryservice.domain.Author;
import com.dsumtsov.bookinventoryservice.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookMapperTest {

    @Test
    public void entityToDtoTest() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");
        book.setNumberOfPages(600);

        Author author = new Author();
        author.setId(1L);
        author.setName("Name Surname Patronymic");
        author.setEmail("test@mail.com");
        author.setBirthday(new Date());
        author.addBook(book);

        BookDTO bookDTO = BookMapper.INSTANCE.toDto(book);

        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getTitle(), bookDTO.getTitle());
        assertEquals(book.getNumberOfPages(), bookDTO.getNumberOfPages());
        assertEquals(book.getAuthor().getName(), bookDTO.getAuthorName());
    }

    @Test
    public void dtoToEntityTest() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Book 1");
        bookDTO.setNumberOfPages(600);
        bookDTO.setAuthorName("Name Surname Patronymic");

        Book bookEntity = BookMapper.INSTANCE.toEntity(bookDTO);

        assertEquals(bookDTO.getId(), bookEntity.getId());
        assertEquals(bookDTO.getTitle(), bookEntity.getTitle());
        assertEquals(bookDTO.getNumberOfPages(), bookEntity.getNumberOfPages());
        assertNull(bookEntity.getAuthor());
    }
}
