package com.huanozong.sale.Fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.huanozong.sale.R
import com.huanozong.sale.adapter.CompanyShenAdapter
import com.huanozong.sale.api.HttpService
import com.huanozong.sale.bean.Company
import com.huanozong.sale.bean.CompanyBean
import com.huanozong.sale.util.SharedPreferencesUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_shenhe.*
import me.yokeyword.fragmentation.SupportFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ShenheListFragment : SupportFragment(){

    var reycleList : RecyclerView? = null
    var adapter : CompanyShenAdapter? = null
    var et_search : EditText? = null
    val statuses = Arrays.asList("审核状态 >","已审核","未审核")
    val lx = Arrays.asList("客户类型 >","重点客户","普通客户","签约客户")
    var swipe_refresh : SmartRefreshLayout?=null
    var companyList = ArrayList<Company>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shenhe,container,false)

        val title = root.findViewById<TextView>(R.id.common_title)
        val search = root.findViewById<TextView>(R.id.common_button)
        et_search = root.findViewById<EditText>(R.id.et_search)
        root.findViewById<ImageButton>(R.id.leftBack).visibility = View.GONE
        reycleList = root.findViewById<RecyclerView>(R.id.rv_shenhelist)

        swipe_refresh = root.findViewById(R.id.swipe_refresh)
        swipe_refresh?.setRefreshHeader(ClassicsHeader(activity))
        swipe_refresh?.setRefreshFooter(ClassicsFooter(activity))
        val sp_status = root.findViewById<TextView>(R.id.sp_status);
        val sp_lx = root.findViewById<TextView>(R.id.sp_lx);

        val adapterlx = ArrayAdapter<String>(activity,R.layout.item_spinner_item,lx)
        val adapterStatus = ArrayAdapter<String>(activity,R.layout.item_spinner_item,statuses)

        search.visibility = View.VISIBLE
        search.text = "搜索"
        search.setOnClickListener {
            page=1
            companyList.clear()
            getData()}
        title.text = "审核列表"

        swipe_refresh?.setOnRefreshListener { clearData() }
        swipe_refresh?.setOnLoadMoreListener { getData() }

        reycleList!!.layoutManager = LinearLayoutManager(activity)
        reycleList!!.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        adapter = CompanyShenAdapter(activity,companyList,false)
        adapter?.setOnItemClickListen { clearData() }
        reycleList!!.adapter = adapter
//        LoadMoreAdapterWrapper(adapter, OnLoad() { pagePosition, pageSize, callback ->
//            getData()
//        })
        setPopuwindou()
        sp_lx.setOnClickListener {
            popoList?.adapter = adapterlx
            popowindou?.showAsDropDown(sp_lx)
            isLx = true}
        sp_status.setOnClickListener {
            popoList?.adapter = adapterStatus
            popowindou?.showAsDropDown(sp_status)
            isLx = false}
        getData()
        return root
    }

    var page = 1
    var popoList : ListView ? = null
    var popowindou : PopupWindow ? = null
    var isLx = false
    var indexlx = 0
    var indexstatus = 0
    private fun setPopuwindou() {
        val view = LayoutInflater.from(activity).inflate(R.layout.popuwindou_list,null)
        popowindou = PopupWindow(view,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        popowindou?.isOutsideTouchable = true
        popoList = view.findViewById<ListView>(R.id.popu_windou)
        popoList?.setOnItemClickListener { adapterView, view, i, l ->
            popowindou?.dismiss()
            if (isLx){
                indexlx = i
                sp_lx.text = lx[i]
                if (i==0){
                    sp_lx.setTextColor(Color.GRAY)
                }else{
                    sp_lx.setTextColor(Color.GREEN)
                }
            }else{
                indexstatus = i
                sp_status.text = statuses[i]
                if (i==0){
                    sp_status.setTextColor(Color.GRAY)
                }else{
                    sp_status.setTextColor(Color.GREEN)
                }
            }
        }
    }

    var map = HashMap<String,String>()
    private fun getData() {
        map.put("uid",SharedPreferencesUtil.queryUserID(activity).toString())
        map.put("aid","7")
        map.put("page",page.toString())
        if (indexlx!=0){
            map.put("lx",indexlx.toString())
        }else{
            if (map.containsKey("lx")){
                map.remove("lx")
            }
        }
        if (indexstatus!=0){
            map.put("status",indexstatus.toString())
        }else{
            if (map.containsKey("status")){
                map.remove("status")
            }
        }

        if(!TextUtils.isEmpty(et_search?.text.toString().trim())){
            map.put("keyword",et_search?.text.toString().trim())
        }else if (map.containsKey("keyword")){
            map.remove("keyword")
        }
        Log.e("tag",map.toString())
        HttpService.getService()
            .getCompanyShenhe(map)
            .enqueue(object : Callback<CompanyBean>{
                override fun onFailure(call: Call<CompanyBean>, t: Throwable) {
                    swipe_refresh?.finishLoadMore()
                    swipe_refresh?.finishRefresh()
                }
                override fun onResponse(call: Call<CompanyBean>, response: Response<CompanyBean>) {
                    val company : CompanyBean? = response.body()
                    if (company != null) {
                        companyList.addAll(company.data)
                        adapter?.companyList = companyList
                        adapter?.notifyDataSetChanged()
                        if (page<company.page.last_page){
                            page++
                            swipe_refresh?.finishLoadMore()
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

    private fun clearData(){
        companyList.clear()
        indexlx=0
        indexstatus=0
        sp_lx.setText(lx[0])
        sp_status.setText(statuses[0])
        sp_lx.setTextColor(Color.GRAY)
        sp_status.setTextColor(Color.GRAY)
        et_search?.setText("")
        page = 1
        getData()
    }
}