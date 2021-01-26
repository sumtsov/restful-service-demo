package com.dsumtsov.book.library.service;

import com.dsumtsov.book.library.exception.ResourceExistsException;
import com.dsumtsov.book.library.exception.ResourceNotFoundException;
import com.dsumtsov.book.library.dto.AuthorDTO;
import com.dsumtsov.book.library.service.impl.AuthorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static java.lang.Long.valueOf;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:create-test-data.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:delete-test-data.sql", executionPhase = AFTER_TEST_METHOD)
@Transactional
public class AuthorServiceTest {

    @Autowired
    AuthorServiceImpl authorService;

    @Test
    public void saveAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Mark Twain");
        authorDTO.setEmail("marktwain@mail.com");
        authorDTO.setBirthday("1835-11-30");

        AuthorDTO saved = authorService.saveAuthor(authorDTO);

        assertEquals(valueOf(3L), saved.getId());
        assertEquals(authorDTO.getName(), saved.getName());
        assertEquals(authorDTO.getEmail(), saved.getEmail());
        assertEquals(authorDTO.getBirthday(), saved.getBirthday());
    }

    @Test(expected = ResourceExistsException.class)
    public void saveAuthor_resourceExistsException() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Arthur Conan Doyle");
        authorDTO.setEmail("conandoyle@mail.com");
        authorDTO.setBirthday("1859-05-22");

        authorService.saveAuthor(authorDTO);
    }

    @Test
    public void getAuthor() {
        AuthorDTO authorDTO = authorService.getAuthor(2L);

        assertEquals(valueOf(2L), authorDTO.getId());
        assertEquals("Arthur Conan Doyle", authorDTO.getName());
        assertEquals("conandoyle@mail.com", authorDTO.getEmail());
        assertEquals("1859-05-22", authorDTO.getBirthday());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAuthor_resourceNotFoundException() {
        authorService.getAuthor(4L);
    }

    @Test
    public void updateAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Arthur Conan Doyle");
        authorDTO.setEmail("conandoyle@mail.com");
        authorDTO.setBirthday("1858-05-22");

        AuthorDTO updated = authorService.updateAuthor(2L, authorDTO);

        assertEquals(valueOf(2L), updated.getId());
        assertEquals(authorDTO.getName(), updated.getName());
        assertEquals(authorDTO.getEmail(), updated.getEmail());
        assertEquals(authorDTO.getBirthday(), updated.getBirthday());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateAuthor_resourceNotFoundException() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Arthur Conan Doyle");
        authorDTO.setEmail("conandoyle@mail.com");
        authorDTO.setBirthday("1858-05-22");

        authorService.updateAuthor(4L, authorDTO);
    }

    @Test(expected = ResourceExistsException.class)
    public void updateAuthor_resourceExistsException() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Stephen Edwin King");
        authorDTO.setEmail("conandoyle@mail.com");
        authorDTO.setBirthday("1948-09-21");

        authorService.updateAuthor(3L, authorDTO);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteAuthor() {
        authorService.deleteAuthor(2L);
        authorService.getAuthor(2L);
    }
}
