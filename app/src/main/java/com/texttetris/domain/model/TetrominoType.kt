package com.texttetris.domain.model

/**
 * 俄罗斯方块类型枚举
 *
 * 定义 7 种经典的俄罗斯方块形状：
 * - I: 一字形，4 格
 * - O: 正方形，2x2
 * - T: T 形
 * - S: S 形（反 Z）
 * - Z: Z 形（反 S）
 * - J: J 形（反 L）
 * - L: L 形
 *
 * @property color 方块对应的颜色
 * @property width 方块最小包围盒宽度
 * @property height 方块最小包围盒高度
 */
enum class TetrominoType(val color: TetrominoColor, val width: Int, val height: Int) {
    I(TetrominoColor.CYAN, 4, 1),
    O(TetrominoColor.YELLOW, 2, 2),
    T(TetrominoColor.PURPLE, 3, 2),
    S(TetrominoColor.GREEN, 3, 2),
    Z(TetrominoColor.RED, 3, 2),
    J(TetrominoColor.BLUE, 3, 2),
    L(TetrominoColor.ORANGE, 3, 2)
}
