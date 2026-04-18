package com.texttetris.domain.usecase

import com.texttetris.domain.model.GameBoard
import com.texttetris.domain.model.Position
import com.texttetris.domain.model.Tetromino
import com.texttetris.util.GameLogger

/**
 * 移动方块用例
 *
 * 负责处理方块的各种移动逻辑：
 * - 向左/向右/向下移动
 * - 碰撞检测（边界和已锁定方块）
 * - 快速下落（多行一次移动）
 */
class MoveTetrominoUseCase {

    /**
     * 向左移动
     *
     * @param tetromino 当前方块
     * @param board 游戏棋盘
     * @return 移动后的方块（如果碰撞则返回原方块）
     */
    fun moveLeft(tetromino: Tetromino, board: GameBoard): Tetromino {
        val newPos = tetromino.position + Position(-1, 0)
        val newTetromino = tetromino.moveTo(newPos)
        val canPlace = canPlace(newTetromino, board)
        GameLogger.d("moveLeft: from (${tetromino.position.x}, ${tetromino.position.y}) to (${newPos.x}, ${newPos.y}), canPlace: $canPlace")
        return if (canPlace) newTetromino else tetromino
    }

    /**
     * 向右移动
     */
    fun moveRight(tetromino: Tetromino, board: GameBoard): Tetromino {
        val newPos = tetromino.position + Position(1, 0)
        val newTetromino = tetromino.moveTo(newPos)
        val canPlace = canPlace(newTetromino, board)
        GameLogger.d("moveRight: from (${tetromino.position.x}, ${tetromino.position.y}) to (${newPos.x}, ${newPos.y}), canPlace: $canPlace")
        return if (canPlace) newTetromino else tetromino
    }

    /**
     * 向下移动一行
     */
    fun moveDown(tetromino: Tetromino, board: GameBoard): Tetromino {
        val newPos = tetromino.position + Position(0, 1)
        val newTetromino = tetromino.moveTo(newPos)
        return if (canPlace(newTetromino, board)) newTetromino else tetromino
    }

    /**
     * 向下移动多行（快速下落/拖拽）
     *
     * @param tetromino 当前方块
     * @param board 游戏棋盘
     * @param rows 向下移动的行数
     * @return 移动后的方块
     */
    fun moveDownBy(tetromino: Tetromino, board: GameBoard, rows: Int): Tetromino {
        var current = tetromino
        repeat(rows) {
            val newPos = current.position + Position(0, 1)
            val newTetromino = current.moveTo(newPos)
            if (canPlace(newTetromino, board)) {
                current = newTetromino
            } else {
                GameLogger.d("moveDownBy: blocked at row ${current.position.y}")
                return@repeat
            }
        }
        return current
    }

    /**
     * 检查方块是否可以放置在当前位置
     *
     * 检测是否超出边界或与已锁定方块碰撞
     */
    fun canPlace(tetromino: Tetromino, board: GameBoard): Boolean {
        return tetromino.absoluteBlocks().all { board.isEmptyPosition(it) }
    }

    /**
     * 检查方块是否可以继续下落
     */
    fun canMoveDown(tetromino: Tetromino, board: GameBoard): Boolean {
        val newPos = tetromino.position + Position(0, 1)
        val newTetromino = tetromino.moveTo(newPos)
        return canPlace(newTetromino, board)
    }
}