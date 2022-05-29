package com.x.lib_base.utils.util

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process

/**
 *  获取系统相关信息的工具类
 */
object OSUtil {

    /**
     *  获取进程id
     */
    fun getPid(): Int {
        return Process.myPid()
    }

    /**
     *  根据pid获取到相应的进程名称
     */
    fun getAppName(context: Context, pID: Int): String {
        var processName = ""
        val am = context.getSystemService(Application.ACTIVITY_SERVICE) as ActivityManager
        val l: List<*> = am.runningAppProcesses
        val i = l.iterator()
        while (i.hasNext()) {
            val info = i.next() as ActivityManager.RunningAppProcessInfo
            try {
                if (info.pid == pID) {
                    processName = info.processName
                    return processName
                }
            } catch (e: Exception) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName
    }

    /**
     *  判断进程名是不是当前应用进程
     */
    fun isAppProcess(context: Context, processAppName: String?) : Boolean{
        if (processAppName != null && processAppName.equals(context.packageName,ignoreCase = true)){
            return true
        }
        return false
    }

}