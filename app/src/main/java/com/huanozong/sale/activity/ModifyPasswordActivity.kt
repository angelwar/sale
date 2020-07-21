package com.huanozong.sale.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.huanozong.sale.R
import kotlinx.android.synthetic.main.common_title_layout.*

class ModifyPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_password)

        leftBack.setOnClickListener { finish() }
        common_title.text = "修改密码"
    }
}