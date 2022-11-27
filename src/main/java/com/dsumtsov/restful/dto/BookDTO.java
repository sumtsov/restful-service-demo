package com.dsumtsov.restful.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BookDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @NotEmpty(message = "{field.empty}")
    private String title;
    @NotNull(message = "{field.empty}")
    private Integer numberOfPages;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String authorName;
}
