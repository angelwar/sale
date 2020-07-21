package com.huanozong.sale.Fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.huanozong.sale.R
import com.huanozong.sale.activity.LoginActivity
import com.huanozong.sale.activity.ModifyPasswordActivity
import com.huanozong.sale.activity.OrderListActivity
import com.huanozong.sale.util.SharedPreferencesUtil
import me.yokeyword.fragmentation.SupportFragment

class MyFragment : SupportFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.activity_my,container,false)
        val name = root.findViewById<TextView>(R.id.tv_user_name)
        name.text = SharedPreferencesUtil.searchUserName(activity)

        val logout = root.findViewById<Button>(R.id.bt_layout)
        logout.setOnClickListener {

            val dialog = AlertDialog.Builder(activity!!)
                    .setMessage("请选择确认退出登录").setNegativeButton("确定", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            SharedPreferencesUtil.addUserID(activity, -1)
                            startActivity(Intent(activity, LoginActivity::class.java))
                            activity!!.finish()
                        }
                    }).setPositiveButton("取消", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                        }

                    })

            dialog.show()
        }
        val tv_order = root.findViewById<TextView>(R.id.tv_order)
        tv_order.setOnClickListener { startActivity(Intent(activity,OrderListActivity::class.java)) }

        val modifyPassword = root.findViewById<TextView>(R.id.tv_modify)
        modifyPassword.setOnClickListener {
            startActivity(Intent(activity,ModifyPasswordActivity::class.java))
        }
        return root
    }


}