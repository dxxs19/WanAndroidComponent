package com.x.module_main.logic.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.x.module_main.logic.model.DataX

/**
 * @desc  exportSchema = true , 导出 schema文件，需要在 gradle中配置路径
 * @author wei
 * @date  2022/3/6
 **/
@Database(entities = [DataX::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    // 用于获取之前编写的Dao的实例，只需要进行方法声明就可以了，具体的方法实现是由Room在底层自动完成
    abstract fun articleDao(): ArticleDao

    companion object {
        private val DATABASE_NAME = "wan_android_db"
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build().apply { instance = this }
        }
    }
}