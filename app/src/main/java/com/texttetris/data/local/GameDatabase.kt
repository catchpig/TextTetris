package com.texttetris.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * 游戏数据库
 *
 * Room 数据库单例，管理游戏记录表
 * 使用 [getInstance] 获取实例，保证全局唯一
 *
 * @see GameRecordEntity 游戏记录实体
 * @see GameRecordDao 数据访问对象
 */
@Database(entities = [GameRecordEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameRecordDao(): GameRecordDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getInstance(context: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "texttetris_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}