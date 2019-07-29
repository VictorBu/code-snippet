package com.karonda.springboot2mongodb.service.impl;

import com.karonda.springboot2mongodb.entity.User;
import com.karonda.springboot2mongodb.repository.UserMongoRepository;
import com.karonda.springboot2mongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MongoRepository")
@Service
public class UserServiceMongoRepository implements UserService {

    @Autowired
    private UserMongoRepository userMongoRepository;

    @Override
    public User save(User user) {
        return userMongoRepository.save(user);
    }

    @Override
    public void deleteById(String id) {
        userMongoRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userMongoRepository.deleteAll();
    }

    @Override
    public Iterable<User> findAll() {
        return userMongoRepository.findAll();
    }

    @Override
    public Iterable<User> findAll(Integer pageNum, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userMongoRepository.findAll(pageable);
    }

    @Override
    public List<User> findAllByName(String name) {
        return userMongoRepository.findByName(name);
    }

    @Override
    public Page<User> findAllByName(Integer pageNum, Integer pageSize, String name) {

        User user = new User();
        user.setName(name);

        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<User> userExample = Example.of(user, matcher);

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        return userMongoRepository.findAll(userExample, pageable);
    }
}
