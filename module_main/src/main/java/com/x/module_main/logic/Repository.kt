package com.x.module_main.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.x.lib_application.appContext
import com.x.module_main.logic.dao.AppDatabase
import com.x.module_main.logic.dao.ArticleDao
import com.x.module_main.logic.model.DataX
import com.x.module_main.logic.network.WanAndroidNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.concurrent.thread

/**
 * @desc 仓库层的统一封装入口
 * @author wei
 * @date  2022/3/1
 **/
object Repository {

    private var articleDao: ArticleDao? = null

    init {
        /**
         *  初始化数据库、表
         */
        val database = AppDatabase.getDatabase(appContext)
        this.articleDao = database.articleDao()
    }

    fun insertArticles(articles: List<DataX>) {
        thread {
            articleDao?.inserArticle(articles)
        }
    }

    fun getAllArticlesLive(): LiveData<List<DataX>>? {
        return articleDao?.getAllArticles()
    }

    /**
     *  liveData() 函数是 lifecycle-livedata-ktx 库提供的一个非常强大且好用的功能，它可以自动构建并返回
     *  一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文，这样我们就可以在liveData()函数的代码块
     *  中调用任意的挂起函数了。
     */
    fun getArticles(page: Int) = liveData(Dispatchers.IO) {
        val result = try {
            val articleResponse = WanAndroidNetwork.getArticles(page)
            if (articleResponse.errorCode == 0) {
                val articles = articleResponse.data.datas
                Result.success(articles)
            } else {
                Result.failure(RuntimeException("response status is ${articleResponse.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        // 类似于调用LiveData的setValue()方法来通知数据变化，只不过这里我们无法直接取得返回的LiveData
        // 对象，所以 lifecycle-livedata-ktx 库提供了这样一个替代方法。
        emit(result)
    }

}