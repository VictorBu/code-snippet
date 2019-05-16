package com.karonda.cacheredis.service.impl;

import com.karonda.cacheredis.dao.UserDao;
import com.karonda.cacheredis.entity.User;
import com.karonda.cacheredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@CacheConfig(cacheNames = "user") // 如果使用该注解, 方法中则可以省略 cacheNames 配置
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    // 缓存的最终 key 值为 user::id
    @Cacheable(cacheNames = "user", key = "#id")
    public User get(int id) {
        return userDao.get(id);
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#root.methodName")
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    // condition: 执行方法前判断是否使用注解的功能; unless: 执行方法后，判断是否使用注解提供的功能
    @CachePut(cacheNames = "user", key = "#user.id", condition = "#user.id<10", unless = "#result.status = 1")
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    // 默认规则: 只有一个参数则 key 值取该参数, 如果有多个参数则将这些参数拼接起来作为 key
    @CacheEvict(cacheNames = "user")
    public boolean delete(int id) {
        return userDao.delete(id);
    }

    @Override
    // allEntries 清除 cacheNames 下所有 key; beforeInvocation 方法执行前清除
    @CacheEvict(cacheNames = "user", allEntries = true, beforeInvocation = true)
    public boolean deleteAll() {
        return userDao.deleteAll();
    }
}
