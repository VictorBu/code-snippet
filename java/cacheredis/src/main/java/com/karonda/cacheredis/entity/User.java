package com.karonda.cacheredis.entity;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -7087233615008249439L;

    private int id;
    private String name;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
