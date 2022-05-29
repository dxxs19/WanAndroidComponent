package com.x.module_main.logic.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.x.module_main.logic.model.DataX

/**
 * @desc
 * @author wei
 * @date  2022/3/6
 **/
@ExperimentalPagingApi
class DataRemoteMediator : RemoteMediator<Int, DataX>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, DataX>): MediatorResult {
        /**
         * 在这个方法内将会做三件事
         *
         * 1. 参数 LoadType 有个三个值，关于这三个值如何进行判断
         *      LoadType.REFRESH
         *      LoadType.PREPEND
         *      LoadType.APPEND
         *
         * 2. 请问网络数据
         *
         * 3. 将网络数据插入到本地数据库中
         */
        TODO("Not yet implemented")

    }

}