package com.huanozong.sale.bean;

public class BaseBean {
    private int code;
    private String msg;

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


    public boolean isSuccess(){
        if (getCode()==200){
            return true;
        }else {
            return false;
        }
    }
}
