package com.huanozong.sale.location

import com.baidu.location.BDLocation

interface OnMapLocation{
    fun onLocation(bd : BDLocation)
}