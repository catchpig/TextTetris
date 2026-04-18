package com.texttetris.domain.usecase

import com.texttetris.domain.model.GameBoard
import com.texttetris.util.GameLogger

/**
 * 消行用例
 *
 * 负责检测并消除已填满的行
 */
class ClearLinesUseCase {

    /**
     * 清除已填满的行
     *
     * @param board 当前游戏棋盘
     * @return Pair(清除后的新棋盘, 消除的行数)
     */
    fun clear(board: GameBoard): Pair<GameBoard, Int> {
        val (newBoard, linesCleared) = board.clearFullLines()
        if (linesCleared > 0) {
            GameLogger.i("clearLines: cleared $linesCleared line(s)")
        }
        return Pair(newBoard, linesCleared)
    }
}