package com.karonda.service.impl;

import com.karonda.dao.UserDao;
import com.karonda.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.karonda.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User create(String username, String password){

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        User u = userDao.save(user);
        return u;
    }
}
