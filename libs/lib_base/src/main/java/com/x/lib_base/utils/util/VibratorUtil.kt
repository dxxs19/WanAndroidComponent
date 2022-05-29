package com.x.lib_base.utils.util

import android.app.Activity
import android.app.Service
import android.content.Context
import android.os.Vibrator


class VibratorUtil {
    companion object {
        /**
         * final Activity activity  ：调用该方法的Activity实例
         * long milliseconds ：震动的时长，单位是毫秒
         * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
         * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
         */
        fun Vibrate(context: Context, milliseconds: Long) {
            val vib = context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
            vib.vibrate(milliseconds)
        }

        fun Vibrate(activity: Activity, pattern: LongArray?, isRepeat: Boolean) {
            val vib = activity.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
            vib.vibrate(pattern, if (isRepeat) 1 else -1)
        }
    }
}