package com.karonda.redisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ObjectRunner implements CommandLineRunner {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {

        String key;

        System.out.println("====== Object ======");
        User user = new User();
        user.setId(1);
        user.setName("admin");
        key = "user:id:" + user.getId();
        redisTemplate.opsForValue().set(key, user);
        user = (User)redisTemplate.opsForValue().get(key);
        System.out.println(user.getName());
    }
}
