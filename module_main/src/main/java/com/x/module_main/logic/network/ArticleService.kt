package com.x.module_main.logic.network

import com.x.module_main.logic.model.ArticleResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @desc 直接请求网络
 * @author wei
 * @date  2022/3/1
 **/
interface ArticleService {
    @GET("article/list/{page}/json")
    suspend fun getArticles(@Path("page") page: Int): ArticleResponse
//    @GET("article/list/{page}/json")
//    fun getArticles(@Path("page") page: Int): Call<ArticleResponse>
}