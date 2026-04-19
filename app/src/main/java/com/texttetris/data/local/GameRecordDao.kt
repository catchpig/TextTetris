package com.texttetris.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * 游戏记录数据访问对象（DAO）
 *
 * 定义游戏记录表的数据库操作
 */
@Dao
interface GameRecordDao {
    @Insert
    suspend fun insert(record: GameRecordEntity)

    @Query("SELECT * FROM game_records ORDER BY score DESC")
    suspend fun getAllRecords(): List<GameRecordEntity>

    @Query("SELECT MAX(score) FROM game_records")
    suspend fun getHighScore(): Int?

    @Query("SELECT COUNT(*) FROM game_records")
    suspend fun getTotalGames(): Int

    @Query("SELECT COALESCE(SUM(linesCleared), 0) FROM game_records")
    suspend fun getTotalLinesCleared(): Int
}