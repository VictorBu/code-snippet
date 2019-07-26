package com.karonda.springboot2shardingjdbc.service.impl;

import com.karonda.springboot2shardingjdbc.dao.UserDOMapper;
import com.karonda.springboot2shardingjdbc.dataobject.UserDO;
import com.karonda.springboot2shardingjdbc.service.UserService;
import com.karonda.springboot2shardingjdbc.viewobject.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public Integer add(UserVO user) {

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(user, userDO);

        return userDOMapper.insert(userDO);
    }

    @Override
    public UserVO get(int id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if(userDO == null){
            return null;
        }

        UserVO user = new UserVO();
        BeanUtils.copyProperties(userDO, user);

        return user;
    }
}
