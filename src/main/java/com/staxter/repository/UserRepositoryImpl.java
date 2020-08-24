package com.staxter.repository;

import com.staxter.domain.*;
import com.staxter.domain.user.UserDto;
import com.staxter.exception.DuplicateRecordException;
import com.staxter.exception.UserCreationException;
import com.staxter.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    private  HashMap<String, UserDto> repository;

    Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private UserRepositoryImpl() {
        this.repository = new HashMap<>();
    }

    @Override
    public UserDto registerUser(User user) {
        logger.info("Register User Method Start::");
        System.out.println("Inside user repo" + user.getUserName());
        if (user == null || StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            logger.error("User cannot be created..Something went wrong::");
            throw new UserCreationException("User cannot be created..Something went wrong");
        }else if(user.getUserName().contains(" ")||user.getPassword().contains(" ")){
            logger.error("Username may only contain alphanumeric characters ::");
            throw new UserCreationException("Username may only contain alphanumeric characters ::");
        }

        Collection<String> roles = user.getRoles();

        if (roles == null) {
            roles = new ArrayList<>();
            roles.add("Admin");
        }
        synchronized (this) {
                if (repository.containsKey(user.getUserName())) {
                    logger.error("User with username  " + user.getUserName() + " already exists!");
                    throw new DuplicateRecordException("User with username  " + user.getUserName() + " already exists!");
                }

            UserDto newUser = new UserDto(
                    repository.size() + 1,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUserName(),
                    bCryptPasswordEncoder.encode(user.getPassword()),
                    roles
            );
            repository.put(user.getUserName(), newUser);

            return newUser;

        }

    }

    @Override
    public synchronized UserDto get(String username) {
        return repository
                .entrySet()
                .stream()
                .filter(entry -> Objects.equals(username, entry.getValue().getUserName()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }




    }
