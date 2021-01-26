package com.dsumtsov.book.library.service;

import com.dsumtsov.book.library.exception.ResourceExistsException;
import com.dsumtsov.book.library.exception.ResourceNotFoundException;
import com.dsumtsov.book.library.dto.BookDTO;
import com.dsumtsov.book.library.service.impl.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:create-test-data.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:delete-test-data.sql", executionPhase = AFTER_TEST_METHOD)
@Transactional
public class BookServiceTest {

    @Autowired
    BookServiceImpl bookService;

    @Test
    public void getBook() {
        BookDTO bookDTO = bookService.getBook(2L);

        assertEquals(Long.valueOf(2L), bookDTO.getId());
        assertEquals("The Hound of the Baskervilles", bookDTO.getTitle());
        assertEquals(Integer.valueOf(211), bookDTO.getNumberOfPages());
        assertEquals("Arthur Conan Doyle", bookDTO.getAuthorName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getBook_resourceNotFoundException() {
        bookService.getBook(4L);
    }

    @Test
    public void saveBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("The Man with the Twisted Lip");
        bookDTO.setNumberOfPages(21);

        BookDTO saved = bookService.saveBook(2L, bookDTO);

        assertEquals(Long.valueOf(1L), saved.getId());
        assertEquals(bookDTO.getTitle(), saved.getTitle());
        assertEquals(bookDTO.getNumberOfPages(), saved.getNumberOfPages());
        assertEquals("Arthur Conan Doyle", saved.getAuthorName());
    }

    @Test(expected = ResourceExistsException.class)
    public void saveBook_resourceExistsException() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("The Hound of the Baskervilles");
        bookDTO.setNumberOfPages(21);

        bookService.saveBook(2L, bookDTO);
    }

    @Test
    public void updateBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("The Hound of the Baskervilles");
        bookDTO.setNumberOfPages(300);

        BookDTO updated = bookService.updateBook(2L, bookDTO);

        assertEquals(Long.valueOf(2L), updated.getId());
        assertEquals(bookDTO.getTitle(), updated.getTitle());
        assertEquals(bookDTO.getNumberOfPages(), updated.getNumberOfPages());
        assertEquals("Arthur Conan Doyle", updated.getAuthorName());
    }

    @Test(expected = ResourceExistsException.class)
    public void updateBook_resourceExistsException() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("The Five Orange Pips");
        bookDTO.setNumberOfPages(300);

        bookService.updateBook(2L, bookDTO);
    }

    @Test
    public void getBooksByAuthor() {
        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setId(2L);
        bookDTO1.setTitle("The Hound of the Baskervilles");
        bookDTO1.setNumberOfPages(211);
        bookDTO1.setAuthorName("Arthur Conan Doyle");

        BookDTO bookDTO2 = new BookDTO();
        bookDTO2.setId(3L);
        bookDTO2.setTitle("The Five Orange Pips");
        bookDTO2.setNumberOfPages(20);
        bookDTO2.setAuthorName("Arthur Conan Doyle");

        List<BookDTO> books = bookService.getBooksByAuthor(2L);

        assertTrue(books.contains(bookDTO1));
        assertTrue(books.contains(bookDTO2));
    }

    @Test
    public void getBooksByTitle() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(2L);
        bookDTO.setTitle("The Hound of the Baskervilles");
        bookDTO.setNumberOfPages(211);
        bookDTO.setAuthorName("Arthur Conan Doyle");

        List<BookDTO> books = bookService.getBooksByTitle("The Hound of the Baskervilles");

        assertTrue(books.contains(bookDTO));
    }
}
