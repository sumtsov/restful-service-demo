package com.dsumtsov.restful.service.impl;

import com.dsumtsov.restful.constants.ErrorConstants;
import com.dsumtsov.restful.entity.Author;
import com.dsumtsov.restful.exception.ResourceExistsException;
import com.dsumtsov.restful.exception.ResourceNotFoundException;
import com.dsumtsov.restful.repository.AuthorRepository;
import com.dsumtsov.restful.repository.BookRepository;
import com.dsumtsov.restful.mapper.BookMapper;
import com.dsumtsov.restful.dto.BookDTO;
import com.dsumtsov.restful.entity.Book;
import com.dsumtsov.restful.service.BookService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    public BookDTO getBook(@NonNull Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.BOOK_NOT_FOUND_BY_ID_ERR, id)));
        return bookMapper.toDto(book);
    }

    @Transactional
    public BookDTO saveBook(@NonNull Long id, @NonNull BookDTO bookDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.AUTHOR_NOT_FOUND_BY_ID_ERR, id)));
        Book book = bookMapper.toEntity(bookDTO);
        author.addBook(book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    public BookDTO updateBook(@NonNull Long id, @NonNull BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.BOOK_NOT_FOUND_BY_ID_ERR, id)));
        Author author = book.getAuthor();
        author.getBooks().stream()
                .filter(b -> !b.getId().equals(id))
                .filter(b -> b.getTitle().equalsIgnoreCase(bookDTO.getTitle()))
                .findFirst()
                .ifPresent(b -> {
                    throw new ResourceExistsException(format(ErrorConstants.AUTHOR_BOOK_ALREADY_EXISTS_ERR,
                            author.getName(), bookDTO.getTitle()));
                });
        book.setTitle(bookDTO.getTitle());
        book.setNumberOfPages(bookDTO.getNumberOfPages());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    public void deleteBook(@NonNull Long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.BOOK_NOT_FOUND_BY_ID_ERR, id)));
        bookRepository.deleteById(id);
    }

    public List<BookDTO> getBooksByAuthor(@NonNull Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.AUTHOR_NOT_FOUND_BY_ID_ERR, id)));
        return author.getBooks()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> getBooksByTitle(@NonNull String title) {
        return bookRepository.findBooksByTitle(title)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Scheduled(initialDelayString = "${app.job-initial-delay}", fixedDelayString = "${app.job-delay}")
    public void logNumberOfBooksJob() {
        long books = bookRepository.findAll().size();
        log.info("Total number of books in system: {}", books);
    }
}
