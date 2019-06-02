package com.xiaohui.entity;

/**
 * @Author: xiaohui
 * @Description: infoUser实体类
 * @Date: 2019/6/1 9:20
 */
public class InfoUser {

    private String id;

    private String name;

    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public InfoUser(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public InfoUser() {
    }
}

