package com.huanozong.sale.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.huanozong.sale.Fragment.CompanyListFragment
import com.huanozong.sale.Fragment.MyFragment
import com.huanozong.sale.Fragment.ShenheListFragment
import com.huanozong.sale.R
import com.huanozong.sale.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_head.*
import me.yokeyword.fragmentation.SupportActivity

class HeadActivity : SupportActivity(){
    var isMy = false
    var prePosition = 1
    private val mFragments = arrayOf(ShenheListFragment(),CompanyListFragment(),MyFragment())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_head)

        loadMultipleRootFragment(R.id.frame_head,1,mFragments[0],mFragments[1],mFragments[2])
//        supportFragmentManager.beginTransaction().add(R.id.frame_head,mFragments[1]).commit()

        if(SharedPreferencesUtil.isAdmin(this)){
            tv_shenhe.visibility = View.VISIBLE
        }else{
            tv_shenhe.visibility = View.VISIBLE
        }

        rg_bottom.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.tv_shenhe-> { tv_shenhe.setTextColor(Color.WHITE)
                    tv_my.setTextColor(Color.BLACK)
                    tv_company.setTextColor(Color.BLACK)
                    showHideFragment(mFragments[0], mFragments[prePosition])
                    prePosition=0}
                R.id.tv_company-> { tv_company.setTextColor(Color.WHITE)
                    tv_my.setTextColor(Color.BLACK)
                    tv_shenhe.setTextColor(Color.BLACK)
                    showHideFragment(mFragments[1], mFragments[prePosition])
                    prePosition=1}
                R.id.tv_my-> {
                    tv_my.setTextColor(Color.WHITE)
                    tv_company.setTextColor(Color.BLACK)
                    tv_shenhe.setTextColor(Color.BLACK)
                    showHideFragment(mFragments[2], mFragments[prePosition])
                    prePosition=2}
            }
        }

    }

//    override fun onRestart() {
//        super.onRestart()
//        if (!isMy){
//            var f: CompanyListFragment = mFragments[1] as CompanyListFragment
//            f.updata()
//            }
//    }

    fun onSearchList(v : View){
        startActivity(Intent(this,SearchActivity::class.java))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (event?.action == MotionEvent.ACTION_DOWN){
            if (currentFocus!=null){
                if (currentFocus.windowToken!=null){
                    imm.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                }
            }
        }
        return super.onTouchEvent(event)
    }
}

