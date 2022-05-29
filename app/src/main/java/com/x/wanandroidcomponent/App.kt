package com.x.wanandroidcomponent

import android.content.Context
import android.os.Debug
import com.x.lib_application.BaseApplication
import com.x.lib_base.utils.util.TimerUtil

class App : BaseApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        Debug.startMethodTracing("cost_time")
        TimerUtil.markStartTime()
        super.onCreate()
    }
}