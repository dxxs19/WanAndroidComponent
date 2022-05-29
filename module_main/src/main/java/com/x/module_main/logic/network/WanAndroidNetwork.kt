package com.x.module_main.logic.network

import com.x.lib_network.ServiceCreator
import com.x.module_main.logic.model.ArticleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @desc  统一的网络数据源访问入口，对所有网络请求的API进行封装
 * @author wei
 * @date  2022/3/1
 **/
object WanAndroidNetwork {
    private val articleService = ServiceCreator.create(ArticleService::class.java)

//    suspend fun getArticles(page: Int) = articleService.getArticles(page).await()
    suspend fun getArticles(page: Int): ArticleResponse {
        return articleService.getArticles(page)
    }

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body" +
                                " is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}