package com.x.lib_base.utils.util

import java.util.*

/**
 * @desc  时间操作工作类
 * @author wei
 * @date  2022/3/6
 **/
object TimerUtil {
    private var startTime = 0L
    private var endTime = 0L

    /**
     *  延迟多少毫秒开始，间隔多少毫秒重复
     */
    fun period(delay: Long = 0, period: Long = 1000, block: () -> Unit) {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                block()
            }
        }, delay, period)
    }

    fun markStartTime() {
        startTime = System.currentTimeMillis()
    }

    fun costTime(): Long {
        endTime = System.currentTimeMillis()
        return endTime - startTime
    }

}