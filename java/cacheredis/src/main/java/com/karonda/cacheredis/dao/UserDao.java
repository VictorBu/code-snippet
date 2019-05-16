package com.karonda.cacheredis.dao;

import com.karonda.cacheredis.entity.User;

import java.util.List;

public interface UserDao {

    User get(int id);
    List<User> getAll();
    User update(User user);
    boolean delete(int id);
    boolean deleteAll();
}
