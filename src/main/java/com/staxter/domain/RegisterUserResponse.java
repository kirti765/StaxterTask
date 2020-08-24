package com.staxter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;


@AllArgsConstructor
@Getter
@Setter
public class RegisterUserResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String userName;
}
