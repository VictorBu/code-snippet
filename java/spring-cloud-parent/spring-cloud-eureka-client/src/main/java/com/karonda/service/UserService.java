package com.karonda.service;

import com.karonda.dto.UserLoginDTO;
import com.karonda.entity.User;

public interface UserService {

    User create(String username, String password);

    UserLoginDTO login(String username, String password);
}
