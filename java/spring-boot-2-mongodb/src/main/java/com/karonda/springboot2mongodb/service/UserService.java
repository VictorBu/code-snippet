package com.karonda.springboot2mongodb.service;

import com.karonda.springboot2mongodb.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User save(User user);

    void deleteById(String id);

    void deleteAll();

    Iterable<User> findAll();

    Iterable<User> findAll(Integer pageNum, Integer pageSize);

    List<User> findAllByName(String name);

    Page<User> findAllByName(Integer pageNum, Integer pageSize, String name);
}
