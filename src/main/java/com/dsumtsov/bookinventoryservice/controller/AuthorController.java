package com.dsumtsov.bookinventoryservice.controller;

import com.dsumtsov.bookinventoryservice.dto.AuthorDTO;
import com.dsumtsov.bookinventoryservice.dto.BookDTO;
import com.dsumtsov.bookinventoryservice.service.impl.AuthorServiceImpl;
import com.dsumtsov.bookinventoryservice.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final BookServiceImpl bookService;
    private final AuthorServiceImpl authorService;

    @PostMapping
    public AuthorDTO saveAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        return authorService.saveAuthor(authorDTO);
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthor(@PathVariable Long id) {
        return authorService.getAuthor(id);
    }

    @PutMapping("/{id}")
    public AuthorDTO updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
        return authorService.updateAuthor(id, authorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    @PostMapping("/random")
    public AuthorDTO saveRandomAuthor() {
        return authorService.saveRandomAuthor();
    }

    @PostMapping("/{id}/books")
    public BookDTO saveBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        return bookService.saveBook(id, bookDTO);
    }

    @GetMapping("/{id}/books")
    public List<BookDTO> getAllBooks(@PathVariable Long id) {
        return bookService.getBooksByAuthor(id);
    }
}
