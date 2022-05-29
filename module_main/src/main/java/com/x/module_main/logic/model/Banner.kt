package com.x.module_main.logic.model

/**
 * @desc  横幅广告
 * @author wei
 * @date  2022/3/1
 **/
data class Banner(
    val data: List<BannerData>,
    val errorCode: Int,
    val errorMsg: String
)

data class BannerData(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)