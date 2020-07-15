package com.huanozong.sale.bean;

import java.io.Serializable;
import java.util.List;

public class Company implements Serializable {

    private int id;
    private String company;
    private String brand;
    private String address;
    private int type;
    private List user;
    private long expire_time;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List getUser() {
        return user;
    }

    public void setUser(List user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "公司=" + company +
                ", 品牌=" + brand +
                ", 地址=" + address ;
    }

    public long getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(long expire_time) {
        this.expire_time = expire_time;
    }
}
