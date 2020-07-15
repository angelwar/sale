package com.huanozong.sale.bean;

import java.util.List;

public class CompanyDetail extends BaseBean {
    private Company company;
    private List<CompanyUser> list;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<CompanyUser> getList() {
        return list;
    }

    public void setList(List<CompanyUser> list) {
        this.list = list;
    }
}
