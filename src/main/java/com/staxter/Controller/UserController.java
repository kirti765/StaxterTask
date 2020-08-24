package com.staxter.Controller;

import com.staxter.domain.*;
import com.staxter.service.UserService;

import com.staxter.util.MapperUtil;
import com.staxter.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userservice")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest registerUser) {
        return ResponseEntity.ok(MapperUtil.map(userService.registerUser(registerUser)));
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> loginByUser(@RequestBody LoginUser user) {
        return ResponseEntity
                .ok()
                .header("Authorization", "Bearer " + userService.loginByUser(user))
                .body(new GenericResponse(
                        ResponseUtil.USER_LOGGED_IN_SUCCESSFULLY.getValue(),
                        ResponseUtil.USER_LOGGED_IN_SUCCESSFULLY_DESCRIPTION.getValue()
                ));
    }
}
