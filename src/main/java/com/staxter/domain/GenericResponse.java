package com.staxter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GenericResponse {

    private final String code;
    private final String description;
}
