package com.karonda.springboot2mongodb.repository;

import com.karonda.springboot2mongodb.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserMongoRepository extends MongoRepository<User, String> {

    List<User> findByName(String name);
}
