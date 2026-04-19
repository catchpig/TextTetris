package com.texttetris.domain.repository

import com.texttetris.data.local.GameRecordEntity

/**
 * 游戏记录仓库接口
 *
 * 定义游戏数据持久化的操作契约
 * 实现类负责与 Room 数据库交互
 */
interface GameRepository {
    suspend fun saveGameRecord(record: GameRecordEntity)
    suspend fun getAllRecords(): List<GameRecordEntity>
    suspend fun getHighScore(): Int
    suspend fun getTotalGames(): Int
    suspend fun getTotalLinesCleared(): Int
}