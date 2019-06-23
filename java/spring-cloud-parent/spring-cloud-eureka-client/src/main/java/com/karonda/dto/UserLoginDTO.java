package com.karonda.dto;

import com.karonda.entity.JWT;
import com.karonda.entity.User;

public class UserLoginDTO {

    private JWT jwt;
    private User user;

    public JWT getJwt() {
        return jwt;
    }

    public void setJwt(JWT jwt) {
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
