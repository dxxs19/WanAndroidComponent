package com.x.lib_base.utils.util

import android.content.Context
import android.location.LocationManager

/**
 *  检查各种状态类
 */
object CheckUtil {

    /**
     * 检查GPS是否打开
     * @return
     */
    fun checkGPSIsOpen(context: Context): Boolean {
        val locationMng = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationMng.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}
