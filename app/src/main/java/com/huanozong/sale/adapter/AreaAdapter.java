package com.huanozong.sale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.huanozong.sale.R;
import com.huanozong.sale.bean.AreaBean;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.VH> {

    private Context mContext;
    private List<AreaBean> list;

    public AreaAdapter(Context mContext, List<AreaBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_select,null,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, final int i) {

        vh.tv.setText(list.get(i).getName());
        vh.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(i).setSelect(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tv;
        CheckBox cb;
        public VH(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv_select);
            cb = itemView.findViewById(R.id.cb_select);
        }
    }


}
