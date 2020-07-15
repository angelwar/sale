package com.huanozong.sale.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huanozong.sale.R;

public class MyViewHolder extends BaseViewHolder {
    public LinearLayout ll;
    public LinearLayout ll_a;
    public LinearLayout ll_b;

    public CheckBox cb_level_0,cb_level_1,cb_level_2,cb_a,cb_b;

    public ImageView iv_arrow_0,iv_arrow_1;
    public TextView tv_select_0,tv_select_1,tv_select_2;

    public MyViewHolder(View view) {
        super(view);
        ll = view.findViewById(R.id.ll_ab);
        ll_a = view.findViewById(R.id.ll_a);
        ll_b = view.findViewById(R.id.ll_b);
        cb_a = view.findViewById(R.id.cb_a);
        cb_b = view.findViewById(R.id.cb_b);
        cb_level_0 = view.findViewById(R.id.cb_select_0);
        cb_level_1 = view.findViewById(R.id.cb_select_1);
        cb_level_2 = view.findViewById(R.id.cb_select_2);

        iv_arrow_0 = view.findViewById(R.id.iv_arrow_0);
        iv_arrow_1 = view.findViewById(R.id.iv_arrow_1);

        tv_select_0 = view.findViewById(R.id.tv_select_0);
        tv_select_1 = view.findViewById(R.id.tv_select_1);
        tv_select_2 = view.findViewById(R.id.tv_select_2);

    }
}
