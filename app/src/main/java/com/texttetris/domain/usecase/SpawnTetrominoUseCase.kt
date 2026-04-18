package com.texttetris.domain.usecase

import com.texttetris.domain.model.Position
import com.texttetris.domain.model.Tetromino
import com.texttetris.domain.model.TetrominoType
import com.texttetris.util.GameLogger
import kotlin.random.Random

/**
 * 生成方块用例
 *
 * 负责随机生成下一个游戏方块
 */
class SpawnTetrominoUseCase {
    private val random = Random

    /**
     * 生成新方块
     *
     * @param type 可选，指定方块类型（用于预览等场景）
     * @return 新生成的方块，初始位置在棋盘顶部中央
     */
    fun spawn(type: TetrominoType? = null): Tetromino {
        val pieceType = type ?: TetrominoType.entries[random.nextInt(TetrominoType.entries.size)]
        val tetromino = Tetromino.create(pieceType, Position(3, 0))
        GameLogger.d("spawn: generated ${pieceType.name} at position (3, 0)")
        return tetromino
    }
}