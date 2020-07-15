package com.huanozong.sale.bean;

import java.util.List;

public class OrderListBean extends BaseBean{
    private List<OrderUserBean> data;

    public List<OrderUserBean> getData() {
        return data;
    }

    public void setData(List<OrderUserBean> data) {
        this.data = data;
    }
}
