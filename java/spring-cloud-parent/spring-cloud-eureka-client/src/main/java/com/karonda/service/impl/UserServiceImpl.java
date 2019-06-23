package com.karonda.service.impl;

import com.karonda.client.AuthServiceClient;
import com.karonda.dao.UserDao;
import com.karonda.dto.UserLoginDTO;
import com.karonda.entity.JWT;
import com.karonda.entity.User;
import com.karonda.exception.UserLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.karonda.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    AuthServiceClient authServiceClient;

    @Override
    public User create(String username, String password){

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        User u = userDao.save(user);
        return u;
    }

    @Override
    public UserLoginDTO login(String username, String password) {
        User user = userDao.findByUsername(username);
        if(null == user){
            throw new UserLoginException("error username");
        }
        if(!password.equals(user.getPassword())){
            throw new UserLoginException("erro password");
        }

        JWT jwt = authServiceClient.getToken("Basic ZXVyZWthLWNsaWVudDoxMjM0NTY="
                , "password", username, password); // ZXVyZWthLWNsaWVudDoxMjM0NTY= 为 eureka-client:123456 Base64 加密后的值
        if(null == jwt){
            throw new UserLoginException("error internal");
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setJwt(jwt);
        userLoginDTO.setUser(user);

        return userLoginDTO;
    }
}
