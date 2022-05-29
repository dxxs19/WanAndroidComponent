package com.x.module_main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.x.module_main.logic.Repository
import com.x.module_main.logic.model.DataX
import com.x.module_main.logic.paging.ArticleDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @desc 文章列表页
 * @author wei
 * @date  2022/3/1
 **/
class ArticleViewModel : BaseViewModel() {
    var dbArticles: LiveData<List<DataX>>? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun getArticles() = Pager(PagingConfig(20)) {
            ArticleDataSource()
        }.flow

    fun getAllArticles() {
        dbArticles = Repository.getAllArticlesLive()
    }

    fun insertArticles(articles: List<DataX>) {
        Repository.insertArticles(articles)
    }
}