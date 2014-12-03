package com.turbomanage.storm.entity;

import com.turbomanage.storm.api.Entity;

@Entity
public class UnkownTypeEntity {

    private long id;
    String name;
    String blog;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }
}
