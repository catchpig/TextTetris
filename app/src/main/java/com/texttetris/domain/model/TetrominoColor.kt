package com.texttetris.domain.model

import androidx.compose.ui.graphics.Color

enum class TetrominoColor(val rgb: Long) {
    CYAN(0xFF00FFFF),   // I piece
    YELLOW(0xFFFFFF00),  // O piece
    PURPLE(0xFF8000FF),  // T piece
    GREEN(0xFF00FF00),   // S piece
    RED(0xFFFF0000),     // Z piece
    BLUE(0xFF0000FF),    // J piece
    ORANGE(0xFFFF8000);  // L piece
}

fun TetrominoColor.toComposeColor(): Color = when (this) {
    TetrominoColor.CYAN -> Color(0xFF00FFFF)
    TetrominoColor.YELLOW -> Color(0xFFFFFF00)
    TetrominoColor.PURPLE -> Color(0xFF8000FF)
    TetrominoColor.GREEN -> Color(0xFF00FF00)
    TetrominoColor.RED -> Color(0xFFFF0000)
    TetrominoColor.BLUE -> Color(0xFF0000FF)
    TetrominoColor.ORANGE -> Color(0xFFFF8000)
}
