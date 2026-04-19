package com.texttetris.domain.model

/**
 * 棋盘格子
 *
 * 表示棋盘上的一个格子，可以是空的或有颜色
 *
 * @property color 格子颜色（null 表示为空）
 */
data class Cell(val color: TetrominoColor? = null)
