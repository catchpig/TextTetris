package com.texttetris.domain.model

/**
 * 位置坐标
 *
 * 表示棋盘或方块上的一个二维坐标位置
 *
 * @property x 横坐标（列），从左到右递增
 * @property y 纵坐标（行），从上到下递增
 */
data class Position(val x: Int, val y: Int) {

    /**
     * 位置相加
     *
     * 用于计算方块移动后的新位置
     */
    operator fun plus(other: Position) = Position(x + other.x, y + other.y)
}
