package com.huanozong.sale.activity

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.widget.Toast
import com.huanozong.sale.R
import com.huanozong.sale.api.HttpService
import com.huanozong.sale.bean.BaseBean
import com.huanozong.sale.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_modify_password.*
import kotlinx.android.synthetic.main.common_title_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_password)

        leftBack.setOnClickListener { finish() }
        common_title.text = "修改密码"
        bt_commit.setOnClickListener {
            val first = tv_first.text.toString().trim()
            val two = tv_comfire.text.toString().trim()
            if (TextUtils.isEmpty(first)||TextUtils.isEmpty(two)){
                Toast.makeText(this@ModifyPasswordActivity,"请输入密码",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!first.equals(two)){
                Toast.makeText(this@ModifyPasswordActivity,"2次输入密码不一致",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            updataPassword(first)
        }
    }
    fun updataPassword(first: String) {
        HttpService.getService().changePassword(SharedPreferencesUtil.queryUserID(this),first).enqueue(object :
            Callback<BaseBean>{
            override fun onFailure(call: Call<BaseBean>, t: Throwable) {
                Toast.makeText(this@ModifyPasswordActivity,"修改密码没有成功，请重试",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseBean>, response: Response<BaseBean>) {
                val v = AlertDialog.Builder(this@ModifyPasswordActivity).setTitle("修改密码成功")
                    .setPositiveButton("确定") { p0, p1 ->
                        SharedPreferencesUtil.addUserID(this@ModifyPasswordActivity,-1)
                        startActivity(Intent(this@ModifyPasswordActivity,LoginActivity::class.java))
                    }
                v.create().show()

            }

        })
    }
}