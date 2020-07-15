package com.huanozong.sale.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.huanozong.sale.R;

import java.util.List;

public class SugAdapter extends RecyclerView.Adapter<SugAdapter.VH> {
    Context mContext;
    List listData ;

    class VH extends RecyclerView.ViewHolder{

        TextView tv;
        public VH(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_item_sug);
        }
    }

    public SugAdapter(Context mContext, List listData) {
        this.mContext = mContext;
        this.listData = listData;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sug, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, final int i) {
        vh.tv.setText(((SuggestionResult.SuggestionInfo)listData.get(i)).getKey());

        vh.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listen!=null){
//                    Toast.makeText(mContext,"on click "+i+" item",Toast.LENGTH_SHORT).show();
                    listen.onClick(((SuggestionResult.SuggestionInfo) listData.get(i)).getPt());
//                    SuggestionResult.SuggestionInfo a = (SuggestionResult.SuggestionInfo) listData.get(i);
//                    Log.e("tag","address :"+a.address+"latlay :"+a.getPt());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public interface OnItemClickListen{
        void onClick(LatLng latLng);
    }

    OnItemClickListen listen;

    public void setOnItemClickListen(OnItemClickListen listen){
        this.listen = listen;
    }

}
