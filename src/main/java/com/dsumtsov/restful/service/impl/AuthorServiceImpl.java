package com.dsumtsov.restful.service.impl;

import com.dsumtsov.restful.entity.Author;
import com.dsumtsov.restful.exception.ResourceExistsException;
import com.dsumtsov.restful.exception.ResourceNotFoundException;
import com.dsumtsov.restful.feign.AuthorGeneratorClient;
import com.dsumtsov.restful.mapper.AuthorMapper;
import com.dsumtsov.restful.dto.AuthorDTO;
import com.dsumtsov.restful.dto.response.AuthorGeneratorResponse;
import com.dsumtsov.restful.repository.AuthorRepository;
import com.dsumtsov.restful.service.AuthorService;
import com.dsumtsov.restful.util.DateUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dsumtsov.restful.util.DateUtils.formatBirthday;
import static com.dsumtsov.restful.constants.ErrorConstants.AUTHOR_EMAIL_ALREADY_EXISTS_ERR;
import static com.dsumtsov.restful.constants.ErrorConstants.AUTHOR_NOT_FOUND_BY_ID_ERR;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorGeneratorClient authorGeneratorClient;
    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    @Transactional
    public AuthorDTO saveAuthor(@NonNull AuthorDTO authorDTO) {
        authorRepository.findByEmail(authorDTO.getEmail())
                .ifPresent(a -> {
                    throw new ResourceExistsException(format(AUTHOR_EMAIL_ALREADY_EXISTS_ERR,
                            authorDTO.getEmail()));
                });
        Author author = authorMapper.toEntity(authorDTO);
        return authorMapper.toDto(authorRepository.save(author));
    }

    public AuthorDTO getAuthor(@NonNull Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format(AUTHOR_NOT_FOUND_BY_ID_ERR, id)));
        return authorMapper.toDto(author);
    }

    @Transactional
    public AuthorDTO updateAuthor(@NonNull Long id, @NonNull AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format(AUTHOR_NOT_FOUND_BY_ID_ERR, id)));
        authorRepository.findByEmail(authorDTO.getEmail())
                .filter(a -> !a.getId().equals(id))
                .ifPresent(a -> {
                        throw new ResourceExistsException(format(AUTHOR_EMAIL_ALREADY_EXISTS_ERR,
                                authorDTO.getEmail()));
                });
        author.setName(authorDTO.getName());
        author.setEmail(authorDTO.getEmail());
        author.setBirthday(DateUtils.formatBirthday(authorDTO.getBirthday()));
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Transactional
    public void deleteAuthor(@NonNull Long id) {
        authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format(AUTHOR_NOT_FOUND_BY_ID_ERR, id)));
        authorRepository.deleteById(id);
    }

    public AuthorDTO saveRandomAuthor() {
        AuthorGeneratorResponse response = authorGeneratorClient.retrieveRandomAuthor();
        if (isNotBlank(response.getError()))
            throw new RuntimeException(response.getError());
        Author author = authorMapper.toEntity(response.getResults().get(0));
        return authorMapper.toDto(authorRepository.save(author));
    }
}
