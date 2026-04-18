package com.texttetris.domain.model

/**
 * 俄罗斯方块中的一个方块（Tetromino）
 *
 * 俄罗斯方块有 7 种经典形状：I、O、T、S、Z、J、L
 * 每种形状有 4 种旋转状态（0、90、180、270 度）
 *
 * @property type 方块类型（I、O、T、S、Z、J、L）
 * @property blocks 相对于方块左上角的局部坐标列表
 * @property position 方块在棋盘上的位置（左上角）
 * @property rotation 当前旋转状态（0-3，对应 0°、90°、180°、270°）
 */
data class Tetromino(
    val type: TetrominoType,
    val blocks: List<Position>,
    val position: Position,
    val rotation: Int = 0
) {
    companion object {
        /**
         * 方块形状定义
         *
         * 每种方块类型对应 4 个旋转状态的坐标列表
         * 坐标是相对于方块左上角的局部坐标
         */
        private val SHAPES = mapOf(
            TetrominoType.I to listOf(
                listOf(Position(0,1), Position(1,1), Position(2,1), Position(3,1)),
                listOf(Position(2,0), Position(2,1), Position(2,2), Position(2,3)),
                listOf(Position(0,2), Position(1,2), Position(2,2), Position(3,2)),
                listOf(Position(1,0), Position(1,1), Position(1,2), Position(1,3))
            ),
            TetrominoType.O to listOf(
                listOf(Position(1,0), Position(2,0), Position(1,1), Position(2,1)),
                listOf(Position(1,0), Position(2,0), Position(1,1), Position(2,1)),
                listOf(Position(1,0), Position(2,0), Position(1,1), Position(2,1)),
                listOf(Position(1,0), Position(2,0), Position(1,1), Position(2,1))
            ),
            TetrominoType.T to listOf(
                listOf(Position(1,0), Position(0,1), Position(1,1), Position(2,1)),
                listOf(Position(1,0), Position(1,1), Position(2,1), Position(1,2)),
                listOf(Position(0,1), Position(1,1), Position(2,1), Position(1,2)),
                listOf(Position(1,0), Position(0,1), Position(1,1), Position(1,2))
            ),
            TetrominoType.S to listOf(
                listOf(Position(1,0), Position(2,0), Position(0,1), Position(1,1)),
                listOf(Position(1,0), Position(1,1), Position(2,1), Position(2,2)),
                listOf(Position(1,1), Position(2,1), Position(0,2), Position(1,2)),
                listOf(Position(0,0), Position(0,1), Position(1,1), Position(1,2))
            ),
            TetrominoType.Z to listOf(
                listOf(Position(0,0), Position(1,0), Position(1,1), Position(2,1)),
                listOf(Position(2,0), Position(1,1), Position(2,1), Position(1,2)),
                listOf(Position(0,1), Position(1,1), Position(1,2), Position(2,2)),
                listOf(Position(1,0), Position(0,1), Position(1,1), Position(0,2))
            ),
            TetrominoType.J to listOf(
                listOf(Position(0,0), Position(0,1), Position(1,1), Position(2,1)),
                listOf(Position(1,0), Position(2,0), Position(1,1), Position(1,2)),
                listOf(Position(0,1), Position(1,1), Position(2,1), Position(2,2)),
                listOf(Position(1,0), Position(1,1), Position(0,2), Position(1,2))
            ),
            TetrominoType.L to listOf(
                listOf(Position(2,0), Position(0,1), Position(1,1), Position(2,1)),
                listOf(Position(1,0), Position(1,1), Position(1,2), Position(2,2)),
                listOf(Position(0,1), Position(1,1), Position(2,1), Position(0,2)),
                listOf(Position(0,0), Position(1,0), Position(1,1), Position(1,2))
            )
        )

        /**
         * 创建方块
         *
         * @param type 方块类型
         * @param position 初始位置，默认为棋盘顶部中央 (3, 0)
         * @return 新创建的方块
         */
        fun create(type: TetrominoType, position: Position = Position(3, 0)): Tetromino {
            val blocks = SHAPES[type]?.first() ?: emptyList()
            return Tetromino(type, blocks, position, 0)
        }
    }

    /**
     * 移动方块到新位置
     *
     * @param position 新的位置坐标
     * @return 移动后的新方块（不可变数据，原始方块不变）
     */
    fun moveTo(position: Position) = copy(position = position)

    /**
     * 旋转方块
     *
     * 顺时针旋转 90 度
     * O 型方块旋转后形状不变
     *
     * @return 旋转后的新方块
     */
    fun rotate(): Tetromino {
        val shapeIndex = rotation % 4
        val nextRotation = (rotation + 1) % 4
        val blocks = SHAPES[type]?.get(nextRotation) ?: emptyList()
        return copy(blocks = blocks, rotation = nextRotation)
    }

    /**
     * 获取方块在棋盘上的绝对坐标
     *
     * 将局部坐标转换为棋盘绝对坐标
     *
     * @return 方块包含的所有格子的绝对位置列表
     */
    fun absoluteBlocks(): List<Position> = blocks.map { it + position }
}
