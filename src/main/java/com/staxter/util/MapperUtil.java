package com.staxter.util;


import com.staxter.domain.RegisterUserRequest;
import com.staxter.domain.RegisterUserResponse;
import com.staxter.domain.User;
import com.staxter.domain.user.UserDto;

public final class MapperUtil {

    public static RegisterUserResponse map(User user) {
        return new RegisterUserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName()
        );
    }

    public static User map(UserDto user) {
        return new User(user.getId(), user.getFirstName(), user.getLastName(), user.getUserName(),
                user.getHashedPassword(), null, user.getRoles());
    }

    public static User map(RegisterUserRequest user) {
        return new User(
                -1,
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                null,
                user.getPassword(),
                null
        );
    }
}
