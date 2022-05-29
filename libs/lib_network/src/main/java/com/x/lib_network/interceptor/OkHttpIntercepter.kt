package com.x.lib_network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @desc okhttp3 自定义拦截器
 * @author wei
 * @date  2022/3/8
 **/
class OkHttpInterceptor : Interceptor {
    val TAG = javaClass.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val startTime = System.currentTimeMillis()
        Log.e(TAG, "Sending request ${request.url()} on ${chain.connection()} ${request.headers()}")
        val response = chain.proceed(request)
        val endTime = System.currentTimeMillis()
        Log.e(TAG, "Received response for ${response.request().url()} in " +
                "${endTime - startTime}ms ${response.headers()}")

        return response
    }
}