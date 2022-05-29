package com.x.lib_common

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.ViewCompat

/**
 * @desc
 * @author wei
 * @date  2022/3/1
 **/
interface UIHost {

    val hostView: View

    fun initializeFlow() {
        initData()
        initView()
        initSetup()
    }

    /**
     * 初始化数据
     */
    fun initData() {

    }

    /**
     * 初始化navBar
     */
    fun initView() {

    }

    /**
     * 设置事件或监听等
     */
    fun initSetup() {

    }

    fun setBackgroundColor(@ColorInt color: Int) {
        hostView.setBackgroundColor(color)
    }

    fun setBackgroundResource(@DrawableRes resId: Int) {
        hostView.setBackgroundResource(resId)
    }

    fun setBackground(background: Drawable) {
        ViewCompat.setBackground(hostView, background)
    }
}