package com.dsumtsov.bookinventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class AuthorDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @NotEmpty(message = "{field.empty}")
    private String name;
    @Email(message = "{invalid.email}")
    private String email;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "{invalid.dob}")
    private String birthday;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<BookDTO> books;
}