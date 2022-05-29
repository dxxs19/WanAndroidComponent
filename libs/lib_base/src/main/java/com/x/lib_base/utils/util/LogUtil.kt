package com.x.lib_base.utils.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log

/**
 *  日志打印工具类，如果是debug模式下，则打印，否则不打印
 */
object LogUtil {
    private var isDebug = true

    /**
     *  需要在application里面进行同步设置。LogUtil.INSTANCE.syncIsDebug(getApplicationContext());
     */
    fun syncIsDebug(context: Context) {
        isDebug = context.applicationInfo != null &&
                context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }


    fun v(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }

}