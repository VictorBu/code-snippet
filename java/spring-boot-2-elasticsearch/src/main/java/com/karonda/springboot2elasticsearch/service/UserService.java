package com.karonda.springboot2elasticsearch.service;

import com.karonda.springboot2elasticsearch.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    long count();

    User save(User user);

    void deleteById(String id);

    void deleteAll();

    Iterable<User> findAll();

    Iterable<User> findAll(Integer pageNum, Integer pageSize);

    List<User> findAllByName(String name);

    Page<User> findAllByName(Integer pageNum, Integer pageSize, String name);
}
