package com.huanozong.sale.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.huanozong.sale.R
import com.huanozong.sale.activity.CompanyActivity
import com.huanozong.sale.adapter.CompanyShenAdapter
import com.huanozong.sale.api.HttpService
import com.huanozong.sale.bean.CompanyBean
import com.huanozong.sale.util.SharedPreferencesUtil
import me.yokeyword.fragmentation.SupportFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShenheListFragment : SupportFragment(){

    var reycleList : RecyclerView? = null
    var adapter : CompanyShenAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shenhe,container,false)

        val title = root.findViewById<TextView>(R.id.common_title)
        title.text = "审核列表"

        root.findViewById<ImageButton>(R.id.leftBack).visibility = View.GONE
        reycleList = root.findViewById<RecyclerView>(R.id.rv_shenhelist)

        reycleList!!.layoutManager = LinearLayoutManager(activity)
        reycleList!!.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        adapter = CompanyShenAdapter(activity,null)

        HttpService.getService()
                .getCompanyShenhe(SharedPreferencesUtil.queryUserID(activity),7)
                .enqueue(object : Callback<CompanyBean>{
                    override fun onFailure(call: Call<CompanyBean>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<CompanyBean>, response: Response<CompanyBean>) {
                        val company = response.body()
                        adapter!!.companyList = company!!.data
                        reycleList!!.adapter = adapter
                    }
                })
        return root
    }
}
