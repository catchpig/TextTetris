package com.texttetris.data.repository

import com.texttetris.data.local.GameRecordDao
import com.texttetris.data.local.GameRecordEntity
import com.texttetris.domain.repository.GameRepository

/**
 * 游戏记录仓库实现
 *
 * 负责将游戏记录持久化到本地 Room 数据库
 *
 * @param gameRecordDao 游戏记录 DAO，用于数据库操作
 */
class GameRepositoryImpl(
    private val gameRecordDao: GameRecordDao
) : GameRepository {

    override suspend fun saveGameRecord(record: GameRecordEntity) {
        gameRecordDao.insert(record)
    }

    override suspend fun getAllRecords(): List<GameRecordEntity> {
        return gameRecordDao.getAllRecords()
    }

    override suspend fun getHighScore(): Int {
        return gameRecordDao.getHighScore() ?: 0
    }

    override suspend fun getTotalGames(): Int {
        return gameRecordDao.getTotalGames()
    }

    override suspend fun getTotalLinesCleared(): Int {
        return gameRecordDao.getTotalLinesCleared()
    }
}