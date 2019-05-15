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
public class StringRunner implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate redisTemplate;
//    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(String... args) throws Exception {

        String key;

        // String: 字符串
        System.out.println("====== String ======");
        key = "string:string";
        redisTemplate.opsForValue().set(key, "string.string", 10, TimeUnit.SECONDS);
        redisTemplate.opsForValue().append(key, ".append");
        System.out.println(redisTemplate.opsForValue().get(key));

        key = "string:number";
        redisTemplate.delete(key);
        redisTemplate.opsForValue().increment(key);
        redisTemplate.opsForValue().decrement(key);
        System.out.println(redisTemplate.opsForValue().get(key));

        // Hash: 哈希
        System.out.println("====== Hash ======");
        key = "hash:test";
        redisTemplate.delete(key);
        redisTemplate.opsForHash().put(key, "key1", "value1");

        Map<String, String> map = new HashMap<>();
        map.put("key2", "value2");
        map.put("key3", "1");
        redisTemplate.opsForHash().putAll(key, map);

        System.out.println(redisTemplate.opsForHash().entries(key));

        System.out.println(redisTemplate.opsForHash().keys(key));

        System.out.println(redisTemplate.opsForHash().values(key));

        System.out.println(redisTemplate.opsForHash().get(key, "key1"));

        System.out.println(redisTemplate.opsForHash().increment(key, "key3", 1L));

        redisTemplate.opsForHash().delete(key, "key2");

        // List: 列表
        System.out.println("====== List ======");
        key = "list:test";
        redisTemplate.delete(key);
        redisTemplate.opsForList().rightPush(key, "1");
        redisTemplate.opsForList().leftPush(key, "2");
        redisTemplate.opsForList().rightPushAll(key, new String[]{"3", "4", "5"});
        List<String> list = redisTemplate.opsForList().range(key, 0, -1);
        for(String item : list){
            System.out.print(item + ", ");
        }
        System.out.println();

        // Set: 集合
        System.out.println("====== Set ======");
        key = "set:test";
        String key1 = "set:test1";
        String key2 = "set:test2";
        redisTemplate.delete(key1);
        redisTemplate.delete(key2);
        redisTemplate.opsForSet().add(key1, "value1", "value2", "value1-1", "value1-2");
        System.out.println(redisTemplate.opsForSet().members(key1));
        redisTemplate.opsForSet().remove(key1, "value1-1");
        redisTemplate.opsForSet().move(key1, "value1", key2);

        redisTemplate.opsForSet().add(key2, "value2");
        System.out.println(redisTemplate.opsForSet().intersect(key1, key2));// 交集

        redisTemplate.opsForSet().intersectAndStore(key1, key2, key);// 交集
        System.out.println(redisTemplate.opsForSet().members(key));

        System.out.println(redisTemplate.opsForSet().union(key1, key2));// 并集

        System.out.println(redisTemplate.opsForSet().difference(key1, Arrays.asList(key2)));// 差集


        // zset (sorted set): 有序集合
        // 与 Set 类似，略
    }
}
