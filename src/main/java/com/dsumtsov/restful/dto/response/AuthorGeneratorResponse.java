package com.dsumtsov.restful.dto.response;

import com.dsumtsov.restful.dto.InfoDTO;
import com.dsumtsov.restful.dto.RandomAuthorDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthorGeneratorResponse implements Serializable {
    private List<RandomAuthorDTO> results;
    private InfoDTO info;
    private String error;
}
