package com.huanozong.sale.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.huanozong.sale.R
import com.huanozong.sale.adapter.OrderAdapter
import com.huanozong.sale.api.HttpService
import com.huanozong.sale.bean.OrderListBean
import com.huanozong.sale.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.common_title_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListActivity : AppCompatActivity() {

    val oderUrl= "http://app.hzmtkj.com/portal/login/getAreamap.html?id="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        rv_order.layoutManager = LinearLayoutManager(this)

        rv_order.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        getData()

        leftBack.setOnClickListener { finish() }
        common_title.setText("订单列表")
    }


    fun getData(){
        HttpService.getService().OrderList(SharedPreferencesUtil.queryUserID(this))
                .enqueue(object : Callback<OrderListBean>{
                    override fun onFailure(call: Call<OrderListBean>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<OrderListBean>, response: Response<OrderListBean>) {
                        if (response.body()!!.code == 200){
                            val orderAdapter = OrderAdapter(this@OrderListActivity,response.body()!!.data)
                            rv_order.adapter = orderAdapter
                        }
                    }
                })

    }
}
