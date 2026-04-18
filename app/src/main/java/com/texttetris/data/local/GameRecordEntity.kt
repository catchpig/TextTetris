package com.texttetris.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_records")
data class GameRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val score: Int,
    val level: Int,
    val linesCleared: Int,
    val duration: Long,
    val playedAt: Long
)