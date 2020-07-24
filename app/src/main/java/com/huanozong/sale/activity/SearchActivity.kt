package com.huanozong.sale.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.huanozong.sale.R
import com.huanozong.sale.adapter.CompanyShenAdapter
import com.huanozong.sale.api.HttpService
import com.huanozong.sale.bean.Company
import com.huanozong.sale.bean.CompanyBean
import com.huanozong.sale.util.SharedPreferencesUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_shenhe.*
import kotlinx.android.synthetic.main.fragment_shenhe.et_search
import kotlinx.android.synthetic.main.fragment_shenhe.swipe_refresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        swipe_refresh.setOnLoadMoreListener {
            getDataSearch()}
        swipe_refresh.setOnRefreshListener{
            page=1
            companyList.clear()
            getDataSearch() }
        swipe_refresh.setRefreshHeader(ClassicsHeader(this))
        swipe_refresh.setRefreshFooter(ClassicsFooter(this))
        rv_search.layoutManager = LinearLayoutManager(this)
        rv_search.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = CompanyShenAdapter(this,companyList,true)
        rv_search.adapter = adapter
    }

    var map = HashMap<String,String>()
    var page = 1
    var companyList = ArrayList<Company>()
    var adapter : CompanyShenAdapter? = null
    var str = ""
    private fun getDataSearch() {

        map.put("uid", SharedPreferencesUtil.queryUserID(this).toString())
        map.put("aid","7")
        map.put("page",page.toString())
        map.put("keyword",str)

        Log.e("tag",map.toString())
        HttpService.getService()
            .getCompanyShenhe(map)
            .enqueue(object : Callback<CompanyBean> {
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
                        if (page!=company.page.last_page){
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

    fun onSearch(v : View){
        startSearch()
    }
    fun onBack(v : View){
        finish()
    }
    fun startSearch(){
        str = et_search.text.toString().trim()
        if (TextUtils.isEmpty(str)){
            Toast.makeText(this,"请输入搜索内容!",Toast.LENGTH_SHORT).show()
            return
        }
        getDataSearch()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (event?.action == MotionEvent.ACTION_DOWN){
            if (currentFocus!=null){
                if (currentFocus.windowToken!=null){
                    imm.hideSoftInputFromWindow(currentFocus.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
                }
            }
        }
        return super.onTouchEvent(event)
    }
}