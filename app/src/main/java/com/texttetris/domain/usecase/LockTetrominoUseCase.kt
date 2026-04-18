package com.texttetris.domain.usecase

import com.texttetris.domain.model.GameBoard
import com.texttetris.domain.model.Tetromino

/**
 * 锁定方块用例
 *
 * 负责将下落的方块锁定到棋盘上
 * 将方块的所有格子设置为对应颜色
 */
class LockTetrominoUseCase {

    /**
     * 锁定方块到棋盘
     *
     * 将方块的每个格子设置为对应颜色，表示该位置已被占用
     *
     * @param tetromino 要锁定的方块
     * @param board 当前游戏棋盘
     * @return 锁定后的新棋盘
     */
    fun lock(tetromino: Tetromino, board: GameBoard): GameBoard {
        return tetromino.absoluteBlocks().fold(board) { acc, pos ->
            acc.setCell(pos, tetromino.type.color)
        }
    }
}