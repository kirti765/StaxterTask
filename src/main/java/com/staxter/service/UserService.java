package com.staxter.service;

import com.staxter.domain.*;

public interface UserService {
    User registerUser(RegisterUserRequest registerUser);

    String loginByUser(LoginUser user);
}
