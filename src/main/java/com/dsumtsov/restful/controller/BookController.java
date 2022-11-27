package com.dsumtsov.restful.controller;

import com.dsumtsov.restful.dto.BookDTO;
import com.dsumtsov.restful.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookServiceImpl bookService;

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        return bookService.updateBook(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public List<BookDTO> getBooksByTitle(@RequestParam(value = "title") String title) {
        return bookService.getBooksByTitle(title);
    }
}

