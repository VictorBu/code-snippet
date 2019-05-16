package com.karonda.cacheredis.service;

import com.karonda.cacheredis.entity.User;

public interface UserService {

    User get(int id);
    User update(User user);
    boolean delete(int id);
    boolean deleteAll();
}
