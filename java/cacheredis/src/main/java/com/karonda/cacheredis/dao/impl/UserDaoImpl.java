package com.karonda.cacheredis.dao.impl;

import com.karonda.cacheredis.dao.UserDao;
import com.karonda.cacheredis.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// 模拟数据库操作
@Repository
public class UserDaoImpl implements UserDao {

    private static User user1;
    private static User user2;

    public UserDaoImpl(){

        user1 = new User();
        user1.setId(1);
        user1.setName("admin");
        user1.setStatus(1);

        user2 = new User();
        user2.setId(2);
        user2.setName("newbie");
        user2.setStatus(1);
    }

    @Override
    public User get(int id) {
        System.out.println("调用 dao 层 get: " + id);

        if(id == 1){
            return user1;
        }else if(id == 2){
            return user2;
        }

        return null;
    }

    @Override
    public List<User> getAll() {

        System.out.println("调用 dao 层 getAll");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        return userList;
    }

    @Override
    public User update(User user) {
        System.out.println("调用 dao 层 update");

        if(user.getId() == 1){
            user1.setName(user.getName());
            return user1;
        }else if(user.getId() == 2){
            user2.setName(user.getName());
            return user2;
        }

        return null;
    }

    @Override
    public boolean delete(int id) {
        System.out.println("调用 dao 层 delete: " + id);

        if(id == 1 || id == 2){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        System.out.println("调用 dao 层 deleteAll");

        return true;
    }
}
