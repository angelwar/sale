package com.huanozong.sale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanozong.sale.R;
import com.huanozong.sale.bean.Level1Item;
import com.huanozong.sale.bean.Level2Item;

import java.util.List;

import static com.huanozong.sale.adapter.ExpandableAdapter.TYPE_LEVEL_1;
import static com.huanozong.sale.adapter.ExpandableAdapter.TYPE_LEVEL_2;

public class CommunityAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, MyViewHolder> {


    private List<MultiItemEntity> data;

    @NonNull
    @Override
    public List<MultiItemEntity> getData() {
        return data;
    }

    public void setData(List<MultiItemEntity> data) {
        this.data = data;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CommunityAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_1, R.layout.item_level1);
        addItemType(TYPE_LEVEL_2,R.layout.item_level2);
    }

//    private BaiduMap mBaiduMap;
//    public CommunityAdapter(List<MultiItemEntity> data, BaiduMap baiduMap) {
//        super(data);
//        addItemType(TYPE_LEVEL_1, R.layout.item_level1);
//        addItemType(TYPE_LEVEL_2,R.layout.item_level2);
//
//        this.mBaiduMap = baiduMap;
//
//    }

    @Override
    protected void convert(@NonNull final MyViewHolder helper, final MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_LEVEL_1:
                final Level1Item item1 = (Level1Item) item;
                helper.tv_select_1.setText(item1.getName());

                helper.cb_level_1.setChecked(item1.isSelect());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (item1.isExpanded()) {
                            collapse(pos,false);
                        } else {
                            expand(pos,false);
                        }

                        if (checkInterface!=null){
                            checkInterface.checkBoxChangeCommunity(item1);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_2:
                final Level2Item item2 = (Level2Item) item;
                helper.tv_select_2.setText(item2.getName());
                helper.cb_level_2.setChecked(item2.isSelect());
                helper.cb_a.setChecked(item2.isSelectDoorA());
                helper.cb_b.setChecked(item2.isSelectDoorB());
                if (item2.isaValid()){
                    helper.ll_a.setVisibility(View.VISIBLE);
                }else {
                    helper.ll_a.setVisibility(View.GONE);
                }

                if (item2.isbValid()){
                    helper.ll_b.setVisibility(View.VISIBLE);
                }else {
                    helper.ll_b.setVisibility(View.GONE);
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkInterface!=null){
                            checkInterface.checkBoxChangeCommunity(item2);
                        }

                    }
                });

                helper.cb_level_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBoxClick!=null){
                            checkBoxClick.onCheckBox(helper.cb_level_2.isChecked(),item2);
                        }
                    }
                });

                helper.cb_a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBoxClick!=null){
                            checkBoxClick.onCheckA(helper.cb_a.isChecked(),item2);
                        }
                    }
                });

                helper.cb_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBoxClick!=null){
                            checkBoxClick.onCheckB(helper.cb_b.isChecked(),item2);
                        }
                    }
                });


                break;
        }
    }

    private CheckInterface checkInterface;
    public interface CheckInterface {
        void checkBoxChangeCommunity(Level2Item lv2);
        void checkBoxChangeCommunity(Level1Item lv1);
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    private CheckBoxClick checkBoxClick;
    public interface CheckBoxClick{
        void onCheckA(boolean isCheck,Level2Item door);
        void onCheckB(boolean isCheck,Level2Item door);
        void onCheckBox(boolean isCheck,Level2Item door);
    }
    public void setCheckBoxOnClick(CheckBoxClick checkBoxClick){
        this.checkBoxClick = checkBoxClick;
    }
}
