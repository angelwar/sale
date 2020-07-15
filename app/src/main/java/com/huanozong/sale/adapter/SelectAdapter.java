package com.huanozong.sale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.huanozong.sale.R;
import com.huanozong.sale.bean.Level1Item;

import java.util.List;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.VH> {

    private List<Level1Item> list;
    private Context mContext;
    public SelectAdapter(Context context,List<Level1Item> list) {
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_door,null,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.tv.setText(list.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tv;
        CheckBox cb;
        RecyclerView rv;
        public VH(@NonNull View itemView) {
            super(itemView);
            tv= itemView.findViewById(R.id.tv_select);
            cb= itemView.findViewById(R.id.cb_select);
            rv= itemView.findViewById(R.id.rv_item_door);

        }
    }
}
