package com.karonda.springboot2mongodb.service.impl;

import com.karonda.springboot2mongodb.entity.User;
import com.karonda.springboot2mongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MongoTemplate")
@Service
public class UserServiceMongoTemplate implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User save(User user) {
        return mongoTemplate.save(user);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.remove(User.class);
    }

    @Override
    public Iterable<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public Iterable<User> findAll(Integer pageNum, Integer pageSize) {
        Query query = new Query();
        query.skip(pageNum * pageSize);
        query.limit(pageSize);

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> findAllByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public Page<User> findAllByName(Integer pageNum, Integer pageSize, String name) {

        Query query = new Query();
        query.skip(pageNum * pageSize);
        query.limit(pageSize);

        Criteria criteria = new Criteria();
        criteria.and("name").equals(name);

        query.addCriteria(criteria);

        List<User> userList = mongoTemplate.find(query, User.class);

        long total = mongoTemplate.count(query, User.class);

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Page<User> userPage = new PageImpl(userList, pageable, total);
        return userPage;
    }
}
