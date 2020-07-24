package com.huanozong.sale.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.huanozong.sale.R
import com.huanozong.sale.activity.CompanyActivity
import com.huanozong.sale.activity.CompanyDetailActivity
import com.huanozong.sale.activity.SelectPointActivity1
import com.huanozong.sale.adapter.CompanyAdapter
import com.huanozong.sale.api.HttpService
import com.huanozong.sale.bean.Company
import com.huanozong.sale.bean.CompanyBean
import com.huanozong.sale.util.SharedPreferencesUtil
import com.huanozong.sale.util.TimeUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_shenhe.*
import me.yokeyword.fragmentation.SupportFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyListFragment : SupportFragment(), CompanyAdapter.OnItemClickListen {

    var stime = 0L
    var etime = 0L
    var reycleList : RecyclerView? = null
    var adapter : CompanyAdapter? = null
    var swipe_refresh : SmartRefreshLayout? = null
    var companyList = ArrayList<Company>()
    var page = 1

    override fun onClick(company: Company?) {

        val builder = AlertDialog.Builder(activity!!)
            .setTitle("请选择")
            .setPositiveButton("查看详情") { dialog, which ->
                val intent = Intent(activity, CompanyDetailActivity::class.java)
                intent.putExtra("company", company)
                startActivity(intent)
            }
            .setNegativeButton("地图定位") { dialog, which ->
                if (stime > 1000 && etime > 1000) {
                    val intent = Intent(activity, SelectPointActivity1::class.java)
                    intent.putExtra("company_id", company!!.getId())
                    intent.putExtra("stime", stime)
                    intent.putExtra("etime", etime)
                    startActivity(intent)
                } else {
                    Toast.makeText(activity, "请选择时间", Toast.LENGTH_SHORT).show()
                }
            }

        builder.create().show()



//
//        if(stime == 0L||etime == 0L){
//            Toast.makeText(this.context,"请选择时间再选择点位",Toast.LENGTH_SHORT).show()
//            return
//        }
//        val intent = Intent(activity,SelectPointActivity::class.java)
//        intent.putExtra("company_id",company!!.id)
//        intent.putExtra("stime",stime)
//        intent.putExtra("etime",etime)
//        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_company,container,false)
        val title = root.findViewById<TextView>(R.id.common_title)
        title.text = "客户列表"
        root.findViewById<ImageButton>(R.id.leftBack).visibility = View.GONE
        reycleList = root.findViewById<RecyclerView>(R.id.rv_companylist)
        swipe_refresh = root.findViewById<SmartRefreshLayout>(R.id.swipe_refresh)
        reycleList!!.layoutManager = LinearLayoutManager(activity)
        reycleList!!.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        adapter = CompanyAdapter(activity,companyList)
        reycleList?.adapter = adapter
        adapter!!.setOnItemClickListen(this)
        swipe_refresh?.setRefreshHeader(ClassicsHeader(activity))
        swipe_refresh?.setRefreshFooter(ClassicsFooter(activity))
        swipe_refresh?.setOnRefreshListener { clearData() }
        swipe_refresh?.setOnLoadMoreListener { getData() }
        val common_button = root.findViewById<TextView>(R.id.common_button)
        common_button.visibility = View.VISIBLE
        common_button.setOnClickListener {
            startActivity(Intent(activity, CompanyActivity::class.java))
        }
        getData()
        setTime(root)
        return root
    }

    private fun getData() {
        HttpService.getService()
            .getCompany(SharedPreferencesUtil.queryUserID(activity),page)
            .enqueue(object : Callback<CompanyBean>{
                override fun onFailure(call: Call<CompanyBean>, t: Throwable) {
                }

                override fun onResponse(call: Call<CompanyBean>, response: Response<CompanyBean>) {
                    val company = response.body()
                    if (company != null) {
                        companyList.addAll(company.data)
                        adapter?.companyList = companyList
                        adapter?.notifyDataSetChanged()
                        if (company.page.last_page>page){
                            swipe_refresh?.finishLoadMore()
                            page++
                        }else{
                            swipe_refresh?.finishLoadMoreWithNoMoreData()
                        }
                    }
                    if (swipe_refresh?.isRefreshing!!){
                        swipe_refresh?.finishRefresh()
                    }
                }
            })
    }

    private fun setTime(root: View?) {
        val bt_stime = root!!.findViewById<Button>(R.id.bt_stime)
        val bt_etime = root!!.findViewById<Button>(R.id.bt_etime)

        bt_stime.setOnClickListener{
            //时间选择器
            val pvTime = TimePickerBuilder(activity, OnTimeSelectListener { date, v ->
                stime = TimeUtil.dateToTime(date)
                bt_stime.text = TimeUtil.timestampToDate(TimeUtil.dateToTime(date))
            }).build()
            pvTime.show()
        }

        bt_etime.setOnClickListener{
            //时间选择器
            val pvTime = TimePickerBuilder(activity, OnTimeSelectListener { date, v ->
                etime = TimeUtil.dateToTime(date)
                bt_etime.text = TimeUtil.timestampToDate(TimeUtil.dateToTime(date))
            }).build()
            pvTime.show()
        }

    }

    private fun clearData(){
        companyList.clear()
        et_search?.setText("")
        page = 1
        getData()
    }
}
