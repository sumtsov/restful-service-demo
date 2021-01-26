package com.dsumtsov.book.library.service;

import com.dsumtsov.book.library.dto.AuthorDTO;
import lombok.NonNull;

public interface AuthorService {
    AuthorDTO saveAuthor(@NonNull AuthorDTO authorDTO);
    AuthorDTO getAuthor(@NonNull Long id);
    AuthorDTO updateAuthor(@NonNull Long id, @NonNull AuthorDTO authorDTO);
    void deleteAuthor(@NonNull Long id);
    AuthorDTO saveRandomAuthor();
}
