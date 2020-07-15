package com.huanozong.sale.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanozong.sale.adapter.ExpandableAdapter;

import java.io.Serializable;
import java.util.List;

public class Level1Item extends AbstractExpandableItem<Level2Item> implements MultiItemEntity, Serializable {
    private int id;
    private String name;
    private boolean isSelect = false;

    public Level2Item getDoor() {
        return door;
    }

    public void setDoor(Level2Item door) {
        this.door = door;
    }

    private Level2Item door;

    private boolean location;

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    public List<Level2Item> list;

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

    public List<Level2Item> getList() {
        return list;
    }

    public void setList(List<Level2Item> list) {
        this.list = list;
    }

    @Override
    public int getItemType() {
        return ExpandableAdapter.TYPE_LEVEL_1;
    }

    @Override
    public List<Level2Item> getSubItems() {
        return list;
    }

    @Override
    public void setSubItems(List<Level2Item> list) {
        super.setSubItems(list);
    }

    @Override
    public int getLevel() {
        return 1;
    }
}