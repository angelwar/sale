package com.huanozong.sale.bean;

import java.util.List;

public class CompanyBean extends BaseBean{
    List<Company> data;

    public List<Company> getData() {
        return data;
    }

    public void setData(List<Company> data) {
        this.data = data;
    }
}
