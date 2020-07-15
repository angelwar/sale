package com.huanozong.sale.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.huanozong.sale.R
import com.huanozong.sale.api.HttpService
import com.huanozong.sale.bean.Company
import com.huanozong.sale.bean.CompanyDetail
import kotlinx.android.synthetic.main.activity_company_detail.*
import kotlinx.android.synthetic.main.common_title_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_detail)

        val company = intent.getSerializableExtra("company") as Company

        HttpService.getService().getCostomer(company.id).enqueue(object : Callback<CompanyDetail>{

            override fun onFailure(call: Call<CompanyDetail>, t: Throwable) {
            }

            override fun onResponse(call: Call<CompanyDetail>, response: Response<CompanyDetail>) {

                val obj = response.body()!!.company
                tv_company_name.text = obj.company
                tv_company_brand.text = obj.brand
                tv_company_address.text = obj.address

                tv_company_type.text = if (obj.type==1) "重要客户" else "普通客户"

                val user = response.body()!!.list.get(0)

                tv_user_name.text = user.name
                tv_user_tel.text = user.tel
                tv_user_sex.text = if (user.sex==0) "女" else "男"
                tv_user_old.text = user.old.toString() + "岁"
                tv_user_duties.text = user.duties
            }
        } )

        common_title.text = "客户详情"
        leftBack.setOnClickListener { finish() }



    }

}


