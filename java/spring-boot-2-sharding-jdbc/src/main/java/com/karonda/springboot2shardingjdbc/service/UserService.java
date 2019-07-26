package com.karonda.springboot2shardingjdbc.service;

import com.karonda.springboot2shardingjdbc.viewobject.UserVO;

public interface UserService {

    Integer add(UserVO user);

    UserVO get(int id);
}
