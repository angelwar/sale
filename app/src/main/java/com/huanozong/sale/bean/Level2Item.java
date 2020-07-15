package com.huanozong.sale.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanozong.sale.adapter.ExpandableAdapter;

import java.io.Serializable;

public class Level2Item implements MultiItemEntity, Serializable {

    public boolean isaValid() {
        return aValid;
    }

    public void setaValid(boolean aValid) {
        this.aValid = aValid;
    }

    public boolean isbValid() {
        return bValid;
    }

    public void setbValid(boolean bValid) {
        this.bValid = bValid;
    }

    //判断门是否有效
    public boolean isValid() {
        return aValid||bValid;
    }

    //AB是否可选
    private boolean aValid = false;
    private boolean bValid = false;

    private String communityName;
    //只要AB的状态发生变化，就调整地图上点位的值

    private int a;
    private int b;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {



        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    private boolean selectDoorA = true;
    private boolean selectDoorB = true;
    private boolean select = true;
    private int id;
    private int c_id;
    private int status;
    private int delete_time;
    private String name;
    private String remark;
    private String location;
    private int type;
    private String user;
    private int addtime;
    private int door_a;
    private int door_b;
    private int astime;
    private int aetime;
    private int bstime;
    private int betime;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public boolean isSelectDoorA() {
        return selectDoorA;
    }

    public void setSelectDoorA(boolean selectDoorA) {
        if (!aValid){
            this.selectDoorA = false;
            return;
        }
        this.selectDoorA = selectDoorA;
        this.select = selectDoorA||selectDoorB;
    }

    public boolean isSelectDoorB() {
        return selectDoorB;
    }

    public void setSelectDoorB(boolean selectDoorB) {
        if (!bValid){
            this.selectDoorB = false;
            return;
        }
        this.selectDoorB = selectDoorB;
        this.select = selectDoorA||selectDoorB;
    }


    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setDelete_time(int delete_time) {
        this.delete_time = delete_time;
    }

    public int getDelete_time() {
        return delete_time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setDoor_a(int door_a) {
        this.door_a = door_a;
    }

    public int getDoor_a() {
        return door_a;
    }

    public void setDoor_b(int door_b) {
        this.door_b = door_b;
    }

    public int getDoor_b() {
        return door_b;
    }

    public void setAstime(int astime) {
        this.astime = astime;
    }

    public int getAstime() {
        return astime;
    }

    public void setAetime(int aetime) {
        this.aetime = aetime;
    }

    public int getAetime() {
        return aetime;
    }

    public void setBstime(int bstime) {
        this.bstime = bstime;
    }

    public int getBstime() {
        return bstime;
    }

    public void setBetime(int betime) {
        this.betime = betime;
    }

    public int getBetime() {
        return betime;
    }

    @Override
    public String toString() {
//        return "DoorBean{" +
//                "selectDoorA=" + selectDoorA +
//                ", selectDoorB=" + selectDoorB +
//                ", select=" + select +
//                ", door_a=" + door_a +
//                ", door_b=" + door_b +
//                '}';
        return toJsonString();
    }

    public String toJsonString() {
        return "{\"id\":" + id +
                ",\"door_a\":" + (selectDoorA?1:0) +
                ",\"door_b\":" + (selectDoorB?1:0) +
                "}";
    }

    @Override
    public int getItemType() {
        return ExpandableAdapter.TYPE_LEVEL_2;
    }


    public void setAllab(boolean isCheck) {
        if (aValid){
            this.selectDoorA = isCheck;
        }

        if (bValid){
            this.selectDoorB = isCheck;
        }

    }
}