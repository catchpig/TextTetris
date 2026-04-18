package com.texttetris.domain.model

/**
 * 游戏棋盘
 *
 * 标准俄罗斯方块棋盘为 10 列 × 20 行
 * 使用二维列表存储每个格子的状态
 *
 * @property width 棋盘宽度（列数），默认 10
 * @property height 棋盘高度（行数），默认 20
 * @property cells 格子状态二维列表，cells[y][x] 表示第 y 行第 x 列的格子
 */
data class GameBoard(
    val width: Int = 10,
    val height: Int = 20,
    val cells: List<MutableList<Cell>> = List(height) { MutableList(width) { Cell() } }
) {

    /**
     * 检查位置是否在棋盘范围内
     *
     * @param pos 待检查的位置坐标
     * @return 是否有效
     */
    fun isValidPosition(pos: Position): Boolean =
        pos.x in 0 until width && pos.y in 0 until height

    /**
     * 检查位置是否为空（可以放置方块）
     *
     * @param pos 待检查的位置坐标
     * @return 是否为空
     */
    fun isEmptyPosition(pos: Position): Boolean =
        isValidPosition(pos) && cells[pos.y][pos.x].color == null

    /**
     * 设置格子颜色
     *
     * 用于将方块锁定到棋盘上
     *
     * @param pos 格子位置
     * @param color 方块颜色
     * @return 新棋盘（不可变，原始棋盘不变）
     */
    fun setCell(pos: Position, color: TetrominoColor): GameBoard {
        if (!isValidPosition(pos)) return this
        val newCells = cells.map { it.toMutableList() }
        newCells[pos.y][pos.x] = Cell(color)
        return copy(cells = newCells)
    }

    /**
     * 清除已填满的行
     *
     * 检测所有行，将填满的行消除，上方的行下落
     *
     * @return Pair(清除后的新棋盘, 消除的行数)
     */
    fun clearFullLines(): Pair<GameBoard, Int> {
        // 找出所有填满的行
        val fullLines = cells.indices.filter { row -> cells[row].all { it.color != null } }
        if (fullLines.isEmpty()) return Pair(this, 0)

        // 过滤掉填满的行
        val newCells = cells
            .filterIndexed { index, _ -> index !in fullLines }
            .toMutableList()

        // 从顶部添加空行
        repeat(fullLines.size) { newCells.add(0, MutableList(width) { Cell() }) }

        return Pair(copy(cells = newCells), fullLines.size)
    }

    companion object {
        /**
         * 创建标准游戏棋盘
         *
         * @return 新的空白棋盘
         */
        fun create() = GameBoard()
    }
}
