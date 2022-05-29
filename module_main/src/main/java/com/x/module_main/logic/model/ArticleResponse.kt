package com.x.module_main.logic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * @desc  文章列表
 * @author wei
 * @date  2022/3/1
 **/
data class ArticleResponse(
    val data: Data,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val curPage: Int,
    val datas: List<DataX>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

@Entity(tableName = "article")
data class DataX(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    val id: Int,
    val apkLink: String,
    val audit: Int,
    @ColumnInfo(name = "author", typeAffinity = ColumnInfo.TEXT)
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    @ColumnInfo(name = "desc", typeAffinity = ColumnInfo.TEXT)
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    @ColumnInfo(name = "link", typeAffinity = ColumnInfo.TEXT)
    val link: String,
    val niceDate: String,
    @ColumnInfo(name = "niceSharedDate", typeAffinity = ColumnInfo.TEXT)
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    @ColumnInfo(name = "shareUser", typeAffinity = ColumnInfo.TEXT)
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
//    @Ignore   // 创建数据表时，要么给它赋值，要么注释掉，不然报错。或者写构造函数
//    val tags: List<Tag>,
    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class Tag(
    val name: String,
    val url: String
)