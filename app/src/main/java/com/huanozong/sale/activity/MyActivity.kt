package com.huanozong.sale.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.huanozong.sale.R
import com.huanozong.sale.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        tv_user_name.text = SharedPreferencesUtil.searchUserName(this)
    }
}
