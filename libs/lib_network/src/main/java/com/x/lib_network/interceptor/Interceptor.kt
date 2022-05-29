package com.x.lib_network.interceptor

import android.util.Log
import okhttp3.Interceptor

object Interceptor {

    fun logging(): okhttp3.Interceptor {
        return Interceptor {
            Log.e("logging", "正在执行日志拦截器")
            val request = it.request()
            val response = it.proceed(request)
            Log.e("logging", "日志拦截器执行完毕！" + request.url()+", response ," +
                    response.request().url()+" code: " + response.code())
            response
        }
    }

    fun netWorkInterceptor(): okhttp3.Interceptor {
        return Interceptor {
            Log.e("netWorkInterceptor", "正在执行网络拦截器")
            val request = it.request()
            val response = it.proceed(request)
            Log.e("netWorkInterceptor", "网络拦截器执行完毕！" + request.url()+", response ," +
                    response.request().url()+" code: " + response.code())
            response
        }
    }

}