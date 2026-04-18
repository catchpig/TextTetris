package com.texttetris.domain.repository

import com.texttetris.data.local.GameRecordEntity

interface GameRepository {
    suspend fun saveGameRecord(record: GameRecordEntity)
    suspend fun getAllRecords(): List<GameRecordEntity>
    suspend fun getHighScore(): Int
    suspend fun getTotalGames(): Int
    suspend fun getTotalLinesCleared(): Int
}