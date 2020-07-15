package com.huanozong.sale.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanozong.sale.adapter.ExpandableAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Level0Edit extends AbstractExpandableItem<Level1Item> implements MultiItemEntity, Serializable {
    private boolean isSelect = false ;
    private int id;
    private String name;  //高新区
    public Map<Integer,Level1Item> list;

    private List<Level1Item> itemList;

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


    public Map<Integer, Level1Item> getList() {
        return list;
    }

    public void setList(Map<Integer, Level1Item> list) {
        this.list = list;
//        setItemList();
    }

//    public List<Level1Item> getItemList() {
//        return itemList;
//    }
//
//    public void setItemList() {
//        itemList = new ArrayList<>();
//        for (Map.Entry<Integer,Level1Item> entry: list.entrySet()) {
//            Level1Item item = entry.getValue();
//            itemList.add(item);
//        }
//    }

//    @Override
//    public List<Level1Item> getSubItems() {
//        return itemList;
//    }
}