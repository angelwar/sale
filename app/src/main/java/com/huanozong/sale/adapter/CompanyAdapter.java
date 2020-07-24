package com.huanozong.sale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huanozong.sale.R;
import com.huanozong.sale.bean.Company;
import com.huanozong.sale.util.TimeUtil;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.VH> {
    private Context context;
    public List<Company> companyList;

    public CompanyAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tv,viewGroup,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, @SuppressLint("RecyclerView") final int i) {
        vh.textView.setText(companyList.get(i).getCompany()+" - "+companyList.get(i).getBrand());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listen!=null){
                    listen.onClick(companyList.get(i));
                }
            }
        });
        //1重点客户 2普通客户 3签约客户
        String kehu = "";
        switch (companyList.get(i).getType()){
            case 1:kehu = "重点客户";break;
            case 2:kehu = "普通客户";break;
            case 3:kehu = "签约客户";break;
        }
        vh.type.setText("客户类型："+kehu);
        vh.name.setText("所属销售："+companyList.get(i).getName());
        vh.time.setText("到期时间："+ TimeUtil.timestampToDate(companyList.get(i).getExpire_time()));

    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView textView;
        TextView time;
        TextView name;
        TextView type;

        public VH(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_company_name);
            time = itemView.findViewById(R.id.tv_expire_time);
            name = itemView.findViewById(R.id.tv_name);
            type = itemView.findViewById(R.id.tv_type);
        }
    }


    public interface OnItemClickListen{
        void onClick(Company company);
    }

    OnItemClickListen listen;

    public void setOnItemClickListen(OnItemClickListen listen){
        this.listen = listen;
    }
}
