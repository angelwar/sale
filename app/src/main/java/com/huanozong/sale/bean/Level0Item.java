package com.huanozong.sale.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanozong.sale.adapter.ExpandableAdapter;

import java.io.Serializable;
import java.util.List;

public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity, Serializable {
    private boolean isSelect = false ;
    private int id;
    private String name;  //高新区
    public List<Level1Item> list;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public int getItemType() {
        return ExpandableAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
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

    public List<Level1Item> getList() {
        return list;
    }

    public void setList(List<Level1Item> list) {
        this.list = list;
    }

    @Override
    public List<Level1Item> getSubItems() {
        return list;
    }
}