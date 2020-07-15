package com.huanozong.sale.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huanozong.sale.R;
import com.huanozong.sale.activity.SelectPointActivity1;
import com.huanozong.sale.bean.OrderUserBean;
import com.huanozong.sale.util.TimeUtil;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH> {
    private Context mContext;
    private List<OrderUserBean> list;
    private String url = "http://app.hzmtkj.com/portal/login/getAreamap.html?id=";

    public OrderAdapter(Context mContext, List<OrderUserBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    class VH extends RecyclerView.ViewHolder{
        TextView tv;
        TextView tv_stime;
        TextView tv_etime;
        TextView order_id;
        TextView order_copy;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv= itemView.findViewById(R.id.tv_order_name);
            tv_stime = itemView.findViewById(R.id.tv_stime);
            tv_etime = itemView.findViewById(R.id.tv_etime);
            order_id = itemView.findViewById(R.id.order_id);
            order_copy = itemView.findViewById(R.id.order_copy);
        }
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_list,null,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, final int i) {
        vh.tv.setText(list.get(i).getName());
        vh.order_id.setText("订单id："+list.get(i).getId());
        vh.tv_stime.setText(TimeUtil.timestampToDate(list.get(i).getStime()));
        vh.tv_etime.setText(TimeUtil.timestampToDate(list.get(i).getEtime()));
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(mContext, SelectPointActivity1.class));
                intent.putExtra("order_id",list.get(i).getId());
                mContext.startActivity(intent);
            }
        });

        vh.order_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", url+list.get(i).getId());
                // 将ClipData内容放到系统剪贴板里。
                clipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(mContext,"链接已经复制到剪贴板，请打开浏览器粘贴",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
