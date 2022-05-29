package com.x.lib_base.utils.util

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * @desc  单位转换
 * @author wei
 * @date  2022/1/13
 **/
object SizeUtil {

    fun dp2px(dp: Float) : Float {
        return getTypedValue(TypedValue.COMPLEX_UNIT_DIP, dp)
    }

    /**
     *  将指定 sp 值转换成相应大小的 px
     */
    fun sp2px(sp: Float) : Float {
        return getTypedValue(TypedValue.COMPLEX_UNIT_SP, sp)
    }

    fun getTypedValue(unit: Int, size: Float) : Float {
        return TypedValue.applyDimension(unit, size, getDisplayMetrics())
    }

    fun getDisplayMetrics() : DisplayMetrics {
        return Resources.getSystem().displayMetrics
    }

    fun getScreenWidth() : Float {
        val dm = getDisplayMetrics()
        return sp2px(dm.widthPixels.toFloat())
    }
}