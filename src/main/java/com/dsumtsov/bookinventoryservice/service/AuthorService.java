package com.dsumtsov.bookinventoryservice.service;

import com.dsumtsov.bookinventoryservice.dto.AuthorDTO;
import lombok.NonNull;

public interface AuthorService {
    AuthorDTO saveAuthor(@NonNull AuthorDTO authorDTO);
    AuthorDTO getAuthor(@NonNull Long id);
    AuthorDTO updateAuthor(@NonNull Long id, @NonNull AuthorDTO authorDTO);
    void deleteAuthor(@NonNull Long id);
    AuthorDTO saveRandomAuthor();
}
