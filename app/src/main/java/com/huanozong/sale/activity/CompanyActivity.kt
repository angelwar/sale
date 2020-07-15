package com.huanozong.sale.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.huanozong.sale.R
import com.huanozong.sale.api.HttpService
import com.huanozong.sale.bean.BaseBean
import com.huanozong.sale.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_company.*
import kotlinx.android.synthetic.main.common_title_layout.*
import retrofit2.Call
import retrofit2.Response

/**
 * 添加客户信息
 */
class CompanyActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_company)
    common_title.text = "添加客户"
    leftBack.setOnClickListener { finish()}
    addSubmit.setOnClickListener { addCompany() }
  }

  private fun addCompany() {

      if ( TextUtils.isEmpty(et_company.text)) {
          Toast.makeText(this,"请填写完整信息",Toast.LENGTH_SHORT).show()
          return
      }
      if ( TextUtils.isEmpty(et_brand.text)) {
          Toast.makeText(this,"请填写完整信息",Toast.LENGTH_SHORT).show()
          return
      }
      if ( TextUtils.isEmpty(et_address.text)) {
          Toast.makeText(this,"请填写完整信息",Toast.LENGTH_SHORT).show()
          return
      }
      if ( TextUtils.isEmpty(et_contact.text)) {
          Toast.makeText(this,"请填写完整信息",Toast.LENGTH_SHORT).show()
          return
      }
      if ( TextUtils.isEmpty(et_tel.text)) {
          Toast.makeText(this,"请填写完整信息",Toast.LENGTH_SHORT).show()
          return
      }

      if ( TextUtils.isEmpty(et_age.text)) {
          Toast.makeText(this,"请填写完整信息",Toast.LENGTH_SHORT).show()
          return
      }
      if ( TextUtils.isEmpty(et_duty.text)) {
          Toast.makeText(this,"请填写完整信息",Toast.LENGTH_SHORT).show()
          return
      }

      var sex = 0   //0为女1为男
      if (rb_man.isChecked){
          sex = 1
      }

      //1为重点客户 2为普通客户
      var type = 2

      if(rb_improtent.isChecked){
          type = 1
      }
    HttpService.getService().addCompany(SharedPreferencesUtil.queryUserID(this),
            et_company.text.toString(),
            et_brand.text.toString(),
            et_address.text.toString(),
            type,
            " [{\"name\":\""+et_contact.text.toString()+
                    "\",\"tel\":"+et_tel.text.toString()+
                    ",\"sex\":"+sex+
                    ",\"old\":"+et_age.text.toString() +
                    ",\"duties\":\""+et_duty.text.toString()+"\"}]")
            .enqueue(object : retrofit2.Callback<BaseBean> {
      override fun onFailure(call: Call<BaseBean>, t: Throwable) {
        Toast.makeText(this@CompanyActivity, "添加错误", Toast.LENGTH_SHORT).show()
      }
      override fun onResponse(call: Call<BaseBean>, response: Response<BaseBean>) {
          if(response.body()!!.isSuccess){
              Toast.makeText(this@CompanyActivity, "添加成功", Toast.LENGTH_SHORT).show()
              var intent = Intent(this@CompanyActivity,HeadActivity::class.java)
              finish()
              startActivity(intent)
          }else{
              Toast.makeText(this@CompanyActivity, "添加失败，"+response.body()!!.msg, Toast.LENGTH_SHORT).show()
          }

      }
    })
  }
}




