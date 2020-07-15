package com.huanozong.sale.location

import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.map.MyLocationData

class MyBaiduLocation(var mBaiduMap : BaiduMap) : BDAbstractLocationListener() {

    var mLocation : OnMapLocation? = null
    fun setOnLocation(location : OnMapLocation){
        mLocation = location
    }

    var isFirst = true

    override fun onReceiveLocation(p0: BDLocation?) {

        if(p0==null||mBaiduMap==null){
            return
        }

        //设置地图上的定位箭头
        if(mLocation!=null){
            mLocation!!.onLocation(p0)
        }


//        Log.e("tag","isFirst="+isFirst+",Latitude()="+p0.getLatitude()+",Longitude"+p0.getLongitude())
        // 第一次定位时，将地图位置移动到当前位置
        if (isFirst) {
            val locData = MyLocationData.Builder()
                    .accuracy(p0.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(p0.getDirection()).latitude(p0.getLatitude())
                    .longitude(p0.getLongitude()).build()
            mBaiduMap.setMyLocationData(locData)

            isFirst = false
            val xy = LatLng(p0.getLatitude(),
                    p0.getLongitude())
            val status = MapStatusUpdateFactory.newLatLng(xy)
            mBaiduMap.animateMapStatus(status)
        }
    }
}