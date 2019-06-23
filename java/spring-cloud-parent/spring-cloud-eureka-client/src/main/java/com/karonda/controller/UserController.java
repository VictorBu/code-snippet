package com.karonda.controller;

import com.karonda.dto.UserLoginDTO;
import com.karonda.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.karonda.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User createUser(@RequestParam("username") String username
            , @RequestParam("password") String password){
        return userService.create(username, password);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserLoginDTO login(@RequestParam("username") String username
            , @RequestParam("password") String password){
        return userService.login(username, password);
    }
}
