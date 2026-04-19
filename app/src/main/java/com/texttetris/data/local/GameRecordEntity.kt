package com.texttetris.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 游戏记录实体
 *
 * 表示一局游戏的完整记录，用于持久化存储
 *
 * @property id 记录 ID（自增主键）
 * @property score 最终得分
 * @property level 达到的等级
 * @property linesCleared 消除的总行数
 * @property duration 游戏时长（毫秒）
 * @property playedAt 游玩时间戳
 */
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