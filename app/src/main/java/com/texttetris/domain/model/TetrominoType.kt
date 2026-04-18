package com.texttetris.domain.model

enum class TetrominoType(val color: TetrominoColor, val width: Int, val height: Int) {
    I(TetrominoColor.CYAN, 4, 1),
    O(TetrominoColor.YELLOW, 2, 2),
    T(TetrominoColor.PURPLE, 3, 2),
    S(TetrominoColor.GREEN, 3, 2),
    Z(TetrominoColor.RED, 3, 2),
    J(TetrominoColor.BLUE, 3, 2),
    L(TetrominoColor.ORANGE, 3, 2)
}
