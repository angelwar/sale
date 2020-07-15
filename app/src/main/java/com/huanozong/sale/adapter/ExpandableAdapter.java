package com.huanozong.sale.adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanozong.sale.R;
import com.huanozong.sale.bean.Level0Item;
import com.huanozong.sale.bean.Level1Item;
import com.huanozong.sale.bean.Level2Item;

import java.util.List;

public class ExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, MyViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_LEVEL_2 = 2;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0,R.layout.item_level0);
        addItemType(TYPE_LEVEL_1, R.layout.item_level1);
        addItemType(TYPE_LEVEL_2,R.layout.item_level2);
    }

    @Override
    protected void convert(@NonNull final MyViewHolder helper, final MultiItemEntity item) {

        switch (helper.getItemViewType()){
            case -1:
                final Level0Item lv0 = (Level0Item) item;
                helper.setText(R.id.tv_select_0,lv0.getName());
                helper.setChecked(R.id.cb_select_0,lv0.isSelect());
                helper.tv_select_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (lv0.isExpanded()) {
                            collapse(pos,false);
                            helper.setImageResource(R.id.iv_arrow_0,R.mipmap.arrow);
                        } else {
                            expand(pos,false);
                            helper.setImageResource(R.id.iv_arrow_0,R.mipmap.arrowdown);
                        }
                    }
                });

                //后触发checkBox点击事件
                helper.cb_level_0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean tag = ((CheckBox)v).isChecked();
                        lv0.setSelect(tag);
                        for (Level1Item item : lv0.getList()){
                            item.setSelect(tag);
                            for (Level2Item level2Item :item.getList()){
                                level2Item.setSelect(tag);
                                level2Item.setSelectDoorA(tag);
                                level2Item.setSelectDoorB(tag);
                            }
                        }

                        if (checkInterface!=null){
                            checkInterface.checkBoxChange(lv0);
                        }
                        notifyDataSetChanged();
                    }
                });

                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                helper.setText(R.id.tv_select_1, lv1.getName());
                helper.setChecked(R.id.cb_select_1,lv1.isSelect());
                helper.tv_select_1  .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        Log.d(TAG, "Level 1 item pos: " + pos);
                        if (lv1.isExpanded()) {
                            helper.setImageResource(R.id.iv_arrow_1,R.mipmap.arrow);
                            collapse(pos, false);
                        } else {
                            helper.setImageResource(R.id.iv_arrow_1,R.mipmap.arrowdown);
                            expand(pos, false);
                        }
                    }
                });

                //触发checkBox点击事件
                helper.cb_level_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean tag = ((CheckBox)v).isChecked();

                        //下层全部更改为当前状态
                        lv1.setSelect(tag);
                        for (Level2Item item1 : lv1.getList()){
                            item1.setSelect(tag);
                            item1.setSelectDoorA(tag);
                            item1.setSelectDoorB(tag);
                        }

                        //搜索上层建筑
                        boolean allChildSameState1 = true; //判断该组下面的所有子元素是否处于同一状态
                        //上层建筑跟着改变
                        int allPosition = getParentPositionInAll(helper.getAdapterPosition());
                        if (allPosition!=-1){
                            Level0Item item0 = (Level0Item) getData().get(allPosition);
                            for (Level1Item level1 : item0.getList()){
                                if (level1.isSelect()!=tag){
                                    allChildSameState1 = false;
                                    break;
                                }
                            }
                            if (allChildSameState1){
                                item0.setSelect(tag);
                            }else {
                                item0.setSelect(false);
                            }
                        }

                        if (checkInterface!=null){
                            checkInterface.checkBoxChange(lv1);
                        }
                        notifyDataSetChanged();
                    }
                });
                break;
            case TYPE_LEVEL_2:

                final Level2Item lv2 = (Level2Item) item;
                //刚开始加载改布局默认是Gone
                helper.setText(R.id.tv_select_2, lv2.getName());
                helper.setChecked(R.id.cb_select_2,lv2.isSelect());
                helper.setChecked(R.id.cb_a,lv2.isSelectDoorA());
                helper.setChecked(R.id.cb_b,lv2.isSelectDoorB());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (helper.ll.getVisibility()==View.GONE){
                            helper.setImageResource(R.id.iv_arrow_2,R.mipmap.arrowdown);
                            helper.ll.setVisibility(View.VISIBLE);
                            if (lv2.getDoor_a()==1){
                                helper.ll_a.setVisibility(View.GONE);
                            }
                            if (lv2.getDoor_b()==1){
                                helper.ll_b.setVisibility(View.GONE);
                            }
                        }else {
                            helper.setImageResource(R.id.iv_arrow_2,R.mipmap.arrow);
                            helper.ll.setVisibility(View.GONE);
                        }

                    }
                });

                helper.cb_level_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean tag = ((CheckBox)v).isChecked();
                        //下层全部更改为当前状态
                        lv2.setSelect(tag);
                        lv2.setSelectDoorA(tag);
                        lv2.setSelectDoorB(tag);

                        //搜索上层建筑
                        boolean allChildSameState2 = true; //判断该组下面的所有子元素是否处于同一状态

                        int pos = helper.getAdapterPosition();
                        int positionAtAll = getParentPositionInAll(pos);
                        if (positionAtAll!=-1){
                            Level1Item level1Item = (Level1Item) getData().get(positionAtAll);
                            for (Level2Item item2 : level1Item.getList()){
                                //只要这个列表中有一个是false,就可以跳出循环了
                                if (item2.isSelect()!=tag){
                                    allChildSameState2 = false;
                                    break;
                                }
                            }

                            if (allChildSameState2) {
                                level1Item.setSelect(tag);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
                            } else {
                                level1Item.setSelect(false);//否则一律视为未选中
                            }
                        }
                        if (checkInterface!=null){
                            checkInterface.checkBoxChange(lv2);
                        }
                        notifyDataSetChanged();
                    }
                });
                helper.cb_a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean tag = ((CheckBox)v).isChecked();
                        lv2.setSelectDoorA(tag);
                        lv2.setSelect(lv2.isSelectDoorA()&lv2.isSelectDoorB());
                        //搜索上层建筑
                        boolean allChildSameState2 = true; //判断该组下面的所有子元素是否处于同一状态

                        int pos = helper.getAdapterPosition();
                        int positionAtAll = getParentPositionInAll(pos);
                        if (positionAtAll!=-1){
                            Level1Item level1Item = (Level1Item) getData().get(positionAtAll);
                            for (Level2Item item2 : level1Item.getList()){
                                //只要这个列表中有一个是false,就可以跳出循环了
                                if (item2.isSelect()!=tag){
                                    allChildSameState2 = false;
                                    break;
                                }
                            }

                            if (allChildSameState2) {
                                level1Item.setSelect(tag);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
                            } else {
                                level1Item.setSelect(false);//否则一律视为未选中
                            }
                        }
                        if (checkInterface!=null){
                            checkInterface.checkBoxChange(lv2);
                        }
                        notifyDataSetChanged();
                    }
                });
                helper.cb_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean tag = ((CheckBox)v).isChecked();
                        lv2.setSelectDoorB(tag);
                        lv2.setSelect(lv2.isSelectDoorA()&lv2.isSelectDoorB());
                        //搜索上层建筑
                        boolean allChildSameState2 = true; //判断该组下面的所有子元素是否处于同一状态

                        int pos = helper.getAdapterPosition();
                        int positionAtAll = getParentPositionInAll(pos);
                        if (positionAtAll!=-1){
                            Level1Item level1Item = (Level1Item) getData().get(positionAtAll);
                            for (Level2Item item2 : level1Item.getList()){
                                //只要这个列表中有一个是false,就可以跳出循环了
                                if (item2.isSelect()!=tag){
                                    allChildSameState2 = false;
                                    break;
                                }
                            }

                            if (allChildSameState2) {
                                level1Item.setSelect(tag);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
                            } else {
                                level1Item.setSelect(false);//否则一律视为未选中
                            }
                        }
                        if (checkInterface!=null){
                            checkInterface.checkBoxChange(lv2);
                        }
                        notifyDataSetChanged();
                    }
                });

        }


    }


    public CheckInterface checkInterface;
    public interface CheckInterface {
        void checkBoxChange(Level2Item lv2);
        void checkBoxChange(Level1Item lv1);
        void checkBoxChange(Level0Item lv0);
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }
}
