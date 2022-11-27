package com.dsumtsov.restful.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RandomAuthorDTO implements Serializable {
    private NameDTO name;
    private DobDTO dob;
    private String email;
}
