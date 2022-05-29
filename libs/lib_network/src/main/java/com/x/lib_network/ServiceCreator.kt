package com.x.lib_network

import com.x.lib_network.interceptor.OkHttpInterceptor
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * @desc
 * @author wei
 * @date  2022/3/1
 **/
object ServiceCreator {
    val BASE_URL = "https://www.wanandroid.com/"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
//            .cache()
            .addInterceptor(OkHttpInterceptor())
//            .addInterceptor(com.x.lib_network.interceptor.Interceptor.logging())
            .retryOnConnectionFailure(true)
            .addNetworkInterceptor(com.x.lib_network.interceptor.Interceptor.netWorkInterceptor())
            .build()
    }

    fun okHttpRequest(url: String) {
        val request = Request.Builder()
            .url("")
            .header("Accept","image/webp")
            .addHeader("Charset","UTF-8")
            .build()
        val call = okHttpClient.newCall(request)
        // 同步请求，需要在子线程执行
//        val response = call.execute()
        // 异步请求，可以在主线程执行
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body()?.string()
                // 处理UI需要切换到UI线程处理
            }
        })
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory()
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    // inline 意思是内联函数。表示直接执行方法体内代码，而不会有调用普通方法的压栈出栈耗费资源的行为。
    // 提升了性能，而且增加的代码量是在编译期执行的，对程序可读性不会造成影响。
    // reified 意思是具体化的。reified关键字是用于Kotlin内联函数的,修饰内联函数的泛型,泛型被修饰后,
    // 在方法体里,能从泛型拿到泛型的Class对象,这与java是不同的,java需要泛型且需要泛型的Class类型时,
    // 是要把Class传过来的,但是kotlin不用了
    inline fun <reified T> create(): T = create(T::class.java)
}