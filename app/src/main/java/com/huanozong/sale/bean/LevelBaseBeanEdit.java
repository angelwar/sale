package com.huanozong.sale.bean;

import java.io.Serializable;
import java.util.Map;

public class LevelBaseBeanEdit implements Serializable {

    private int code;
    private String msg;
    private Map<Integer,Level0Edit> data;

    public Map<Integer, Level0Edit> getData() {
        return data;
    }

    public void setData(Map<Integer, Level0Edit> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "DoorMain{" +
                "data=" + data +
                '}';
    }
}
