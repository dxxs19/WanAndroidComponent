package com.x.module_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.x.module_main.logic.Repository

/**
 * @desc 文章列表页
 * @author wei
 * @date  2022/3/1
 **/
class ProjectViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<Int>()

    fun searchArticles(page: Int) {
        searchLiveData.value = page
    }

    val articlesLiveData = Transformations.switchMap(searchLiveData) { page ->
        Repository.getArticles(page)
    }
}