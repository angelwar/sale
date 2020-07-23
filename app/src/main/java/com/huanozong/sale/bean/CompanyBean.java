package com.huanozong.sale.bean;

import java.util.List;

public class CompanyBean extends BaseBean{
    private List<Company> data;
    private BasePage page;

    public BasePage getPage() {
        return page;
    }

    public void setPage(BasePage page) {
        this.page = page;
    }

    public List<Company> getData() {
        return data;
    }

    public void setData(List<Company> data) {
        this.data = data;
    }
}
