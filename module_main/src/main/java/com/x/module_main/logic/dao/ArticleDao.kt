package com.x.module_main.logic.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.x.module_main.logic.model.DataX

/**
 * @desc
 * @author wei
 * @date  2022/3/6
 **/
@Dao
interface ArticleDao {

    @Insert
    fun inserArticle(article: List<DataX>)

    @Query("SELECT * FROM article")
    fun getAllArticles(): LiveData<List<DataX>>

    @Query("select * from article where id = :id")
    fun getArticleById(id: Int): List<DataX>

    @Query("delete from article")
    fun clearArticles()

    // 在 Repository 中通过 RemoteMediator 获取网络分页数据并更新到数据库中
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDBArticle(article: List<DataX>)
    /**
     *  返回一个 PagingSource，这样就可以实现数据库的订阅。
     */
    @Query("SELECT * FROM article")
    fun getDBArticles(): PagingSource<Int, DataX>
}