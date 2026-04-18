package com.texttetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.texttetris.domain.model.GameBoard
import com.texttetris.domain.model.Tetromino
import com.texttetris.domain.model.toComposeColor
import com.texttetris.ui.theme.*

private const val BOARD_WIDTH = 10
private const val BOARD_HEIGHT = 20

@Composable
fun GameBoardCanvas(
    board: GameBoard,
    currentTetromino: Tetromino?,
    modifier: Modifier = Modifier,
    onDragMove: ((Float) -> Unit)? = null
) {
    var lastDragY = remember { 0f }
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val availableWidthPx = with(density) { maxWidth.toPx() }
        val availableHeightPx = with(density) { maxHeight.toPx() }

        // Calculate cell size based on both width and height constraints
        // Board must fit within available space in both dimensions
        val cellSizeByWidth = availableWidthPx / BOARD_WIDTH
        val cellSizeByHeight = availableHeightPx / BOARD_HEIGHT
        val cellSizePx = minOf(cellSizeByWidth, cellSizeByHeight)

        val boardWidthPx = cellSizePx * BOARD_WIDTH
        val boardHeightPx = cellSizePx * BOARD_HEIGHT

        val boardWidthDp = with(density) { boardWidthPx.toDp() }
        val boardHeightDp = with(density) { boardHeightPx.toDp() }

        Box(
            modifier = Modifier
                .size(width = boardWidthDp, height = boardHeightDp)
                .background(Surface)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            lastDragY = offset.y
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            val currentY = change.position.y
                            val delta = currentY - lastDragY
                            if (delta > 0) {
                                onDragMove?.invoke(delta)
                            }
                            lastDragY = currentY
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val actualCellSize = size.width / BOARD_WIDTH

                board.cells.forEachIndexed { y, row ->
                    row.forEachIndexed { x, cell ->
                        val color = cell.color?.toComposeColor() ?: Color.Transparent
                        drawCyberpunkCell(
                            x = x,
                            y = y,
                            color = color,
                            cellSize = actualCellSize,
                            filled = cell.color != null
                        )
                    }
                }

                currentTetromino?.absoluteBlocks()?.forEach { pos ->
                    drawCyberpunkCell(
                        x = pos.x,
                        y = pos.y,
                        color = currentTetromino.type.color.toComposeColor(),
                        cellSize = actualCellSize,
                        filled = true,
                        glowColor = currentTetromino.type.color.toComposeColor()
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawCyberpunkCell(
    x: Int,
    y: Int,
    color: Color,
    cellSize: Float,
    filled: Boolean,
    glowColor: Color = NeonCyan
) {
    val padding = 1f
    val topLeft = Offset(x * cellSize + padding, y * cellSize + padding)
    val cellInnerSize = cellSize - padding * 2

    if (filled) {
        // Glow effect
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(glowColor.copy(alpha = 0.4f), Color.Transparent),
                center = Offset(topLeft.x + cellInnerSize / 2, topLeft.y + cellInnerSize / 2),
                radius = cellInnerSize
            ),
            topLeft = Offset(topLeft.x - 4, topLeft.y - 4),
            size = Size(cellInnerSize + 8, cellInnerSize + 8)
        )

        // Main cell
        drawRect(
            color = color,
            topLeft = topLeft,
            size = Size(cellInnerSize, cellInnerSize)
        )

        // Highlight edge
        drawRect(
            color = Color.White.copy(alpha = 0.3f),
            topLeft = topLeft,
            size = Size(cellInnerSize, 2f)
        )
        drawRect(
            color = Color.White.copy(alpha = 0.2f),
            topLeft = topLeft,
            size = Size(2f, cellInnerSize)
        )

        // Border glow
        drawRect(
            color = color.copy(alpha = 0.8f),
            topLeft = topLeft,
            size = Size(cellInnerSize, cellInnerSize),
            style = Stroke(width = 1f)
        )
    } else {
        // Empty cell - subtle grid
        drawRect(
            color = Color(0xFF1A1A3A),
            topLeft = topLeft,
            size = Size(cellInnerSize, cellInnerSize),
            style = Stroke(width = 1f)
        )
    }
}
