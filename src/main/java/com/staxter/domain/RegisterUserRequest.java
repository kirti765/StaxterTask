package com.staxter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterUserRequest {

    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    @JsonIgnore
    private String plainTextPassword;
    @JsonIgnore
    private String hashedPassword;
    private Collection<String> roles;
}
