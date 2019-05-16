package com.karonda.cacheredis.dao;

import com.karonda.cacheredis.entity.User;

public interface UserDao {

    User get(int id);
    User update(User user);
    boolean delete(int id);
    boolean deleteAll();
}
