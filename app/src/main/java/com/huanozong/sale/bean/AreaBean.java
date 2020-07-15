package com.huanozong.sale.bean;

public class AreaBean {

    //{"id":2,"name":"\u9ad8\u65b0\u533a"},{"id":5,"name":"\u90eb\u53bf"},{"id":4,"name":"\u5929\u5e9c\u65b0\u533a"},
    private int id;
    private String name;

    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

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
}
