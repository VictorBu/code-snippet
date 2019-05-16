package com.karonda.cacheredis.controller;

import com.karonda.cacheredis.entity.User;
import com.karonda.cacheredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public User get(@PathVariable int id){

        return userService.get(id);
    }

    @GetMapping("")
    public List<User> getAll(){

        return userService.getAll();
    }

    @PutMapping("")
    public User update(@RequestBody User user){
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id){
        return userService.delete(id);
    }

    @DeleteMapping("")
    public boolean deleteAll(){
        return userService.deleteAll();
    }
}
