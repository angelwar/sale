package com.huanozong.sale.bean;

public class LoginUser {
    //"id":4947,"username":"zhoushanshan","admin_id":4
    private int id;
    private String username;
    private int admin_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", admin_id=" + admin_id +
                '}';
    }
}
