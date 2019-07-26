package com.karonda.springboot2shardingjdbc.controller;

import com.karonda.springboot2shardingjdbc.service.UserService;
import com.karonda.springboot2shardingjdbc.viewobject.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int add(UserVO user){
        return userService.add(user);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public UserVO get(int id){
        return userService.get(id);
    }
}
