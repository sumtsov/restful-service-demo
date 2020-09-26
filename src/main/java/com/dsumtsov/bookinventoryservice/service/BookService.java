package com.dsumtsov.bookinventoryservice.service;

import com.dsumtsov.bookinventoryservice.dto.BookDTO;
import lombok.NonNull;

import java.util.List;

public interface BookService {
    BookDTO getBook(@NonNull Long id);
    BookDTO saveBook(@NonNull Long id, @NonNull BookDTO bookDTO);
    BookDTO updateBook(@NonNull Long id, @NonNull BookDTO bookDTO);
    void deleteBook(@NonNull Long id);
    List<BookDTO> getBooksByAuthor(@NonNull Long id);
    List<BookDTO> getBooksByTitle(@NonNull String title);
}
