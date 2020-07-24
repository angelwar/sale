package com.huanozong.sale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huanozong.sale.R;
import com.huanozong.sale.activity.CompanyDetailActivity;
import com.huanozong.sale.api.HttpService;
import com.huanozong.sale.bean.BaseBean;
import com.huanozong.sale.bean.Company;
import com.huanozong.sale.bean.CompanyDetail;
import com.huanozong.sale.util.SharedPreferencesUtil;
import com.huanozong.sale.util.TimeUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyShenAdapter extends BaseAdapter<Company> {
    private Context context;
    private boolean isSearch;
    public ArrayList<Company> companyList;

    public CompanyShenAdapter(Context context, ArrayList<Company> companyList,boolean isSearch) {
        this.context = context;
        this.companyList = companyList;
        this.isSearch = isSearch;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shenhe,viewGroup,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        VH vh = (VH) viewHolder;
        vh.textView.setText(companyList.get(i).getCompany()+" - "+companyList.get(i).getBrand());
//        vh.brand.setText(companyList.get(i).getBrand());
        //1重点客户 2普通客户 3签约客户
        String kehu = "";
        switch (companyList.get(i).getType()){
            case 1:kehu = "重点客户";break;
            case 2:kehu = "普通客户";break;
            case 3:kehu = "签约客户";break;
        }
        vh.type.setText("客户类型："+kehu);
        vh.sale.setText("所属销售："+companyList.get(i).getName());
        vh.status.setText(companyList.get(i).getStatus()==1?"审核通过":"未通过");
        vh.time.setText("到期时间："+ TimeUtil.timestampToDate(companyList.get(i).getExpire_time()));
        vh.pass.setVisibility(companyList.get(i).getStatus()!=1&& SharedPreferencesUtil.isAdmin(context) ?View.VISIBLE:View.GONE);
        vh.pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("确认通过审核？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int in) {
                                updataShenhe(companyList.get(i).getId());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();
            }
        });

        vh.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("确认删除该客户？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int in) {
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();
            }
        });

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSearch){
                    Intent intent = new Intent(context, CompanyDetailActivity.class);
                    intent.putExtra("company", companyList.get(i));
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView textView;
        TextView brand;
        TextView type;
        TextView sale;
        TextView time;
        TextView status;
        TextView pass;
        TextView delete;

        public VH(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_company_name);
            time = itemView.findViewById(R.id.tv_expire_time);
//            brand = itemView.findViewById(R.id.tv_company_brand);
            type = itemView.findViewById(R.id.tv_type);
            sale = itemView.findViewById(R.id.tv_sale_name);
            status = itemView.findViewById(R.id.tv_sale_status);
            pass = itemView.findViewById(R.id.tv_pass);
            delete = itemView.findViewById(R.id.tv_delete);
        }
    }


    public interface OnItemClickListen{
        void onClick();
    }

    OnItemClickListen listen;

    public void setOnItemClickListen(OnItemClickListen listen){
        this.listen = listen;
    }

    private void updataStatus(int id,int ty){
        HttpService.getService().changeKh(id,ty).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {

            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {

            }
        });
    }

    private void updataShenhe(int id){
        HttpService.getService().shenhe(id).enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                Toast.makeText(context, "审核成功", Toast.LENGTH_SHORT).show();
                if (listen!=null){
                    listen.onClick();
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                Toast.makeText(context, "审核失败，请检查网络", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
