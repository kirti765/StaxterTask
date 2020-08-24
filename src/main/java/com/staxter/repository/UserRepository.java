package com.staxter.repository;

import com.staxter.domain.User;
import com.staxter.domain.user.UserDto;

public interface UserRepository {
    UserDto registerUser(User user);

    UserDto get(String username);

}
