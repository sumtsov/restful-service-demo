package com.dsumtsov.bookinventoryservice.dto.response;

import com.dsumtsov.bookinventoryservice.dto.InfoDTO;
import com.dsumtsov.bookinventoryservice.dto.RandomAuthorDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthorGeneratorResponse implements Serializable {
    private List<RandomAuthorDTO> results;
    private InfoDTO info;
    private String error;
}
