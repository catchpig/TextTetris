package com.texttetris.domain.usecase

import com.texttetris.domain.model.GameBoard
import com.texttetris.domain.model.Tetromino
import com.texttetris.util.GameLogger

/**
 * 旋转方块用例
 *
 * 负责处理方块旋转逻辑，包含 Wall Kick 补偿机制
 * 当旋转可能导致碰撞时，尝试水平偏移来解决问题
 */
class RotateTetrominoUseCase {
    private val moveUseCase = MoveTetrominoUseCase()

    /**
     * 旋转方块
     *
     * 尝试顺时针旋转，如果直接旋转失败，则尝试 Wall Kick
     *
     * @param tetromino 当前方块
     * @param board 游戏棋盘
     * @return 旋转后的方块（可能位置有偏移）
     */
    fun rotate(tetromino: Tetromino, board: GameBoard): Tetromino {
        val rotated = tetromino.rotate()

        // 尝试直接旋转
        if (moveUseCase.canPlace(rotated, board)) {
            GameLogger.d("rotate: success without kick, rotation: ${rotated.rotation}")
            return rotated
        }

        GameLogger.d("rotate: direct rotation failed, trying wall kicks")

        // Wall Kick 补偿偏移列表
        // 依次尝试：左移、右移、上移
        val kicks = listOf(
            tetromino.position + com.texttetris.domain.model.Position(-1, 0),
            tetromino.position + com.texttetris.domain.model.Position(1, 0),
            tetromino.position + com.texttetris.domain.model.Position(0, -1)
        )

        for ((index, pos) in kicks.withIndex()) {
            val kicked = rotated.copy(position = pos)
            if (moveUseCase.canPlace(kicked, board)) {
                GameLogger.d("rotate: wall kick $index succeeded at offset (${pos.x - tetromino.position.x}, ${pos.y - tetromino.position.y})")
                return kicked
            }
        }

        // 所有旋转都失败，返回原方块
        GameLogger.w("rotate: all rotation attempts failed")
        return tetromino
    }
}