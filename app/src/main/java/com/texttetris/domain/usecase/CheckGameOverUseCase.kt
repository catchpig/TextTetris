package com.texttetris.domain.usecase

import com.texttetris.domain.model.GameBoard
import com.texttetris.domain.model.Tetromino

/**
 * 检查游戏结束用例
 *
 * 负责判断游戏是否结束
 * 当新生成的方块无法放置在起始位置时，游戏结束
 */
class CheckGameOverUseCase {

    /**
     * 检查游戏是否结束
     *
     * 检查方块是否可以放置在棋盘上
     * 如果不能放置，说明新方块生成位置已被占用，游戏结束
     *
     * @param tetromino 尝试放置的方块
     * @param board 游戏棋盘
     * @return 是否游戏结束
     */
    fun isGameOver(tetromino: Tetromino, board: GameBoard): Boolean {
        val moveUseCase = MoveTetrominoUseCase()
        return !moveUseCase.canPlace(tetromino, board)
    }
}