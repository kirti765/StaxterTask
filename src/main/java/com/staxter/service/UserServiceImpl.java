package com.staxter.service;

import com.staxter.domain.*;
import com.staxter.exception.IncorrectUserNamePasswordException;
import com.staxter.filters.JwtUtil;
import com.staxter.repository.UserRepository;
import com.staxter.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User registerUser(RegisterUserRequest user) {
        return MapperUtil.map(userRepository.registerUser(MapperUtil.map(user)));
    }

    @Override
    public String loginByUser(LoginUser user) {
        logger.info("login User Method Start::");
        if (user == null || StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            logger.error(" username or password cannot be empty::");
            throw new IncorrectUserNamePasswordException("username or password cannot be empty");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUserName(),
                user.getPassword())
        );
        final String token = jwtTokenUtil.generateToken(user);
        logger.info("login User Method End::");
        return token;
    }
}
