package com.texttetris.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.texttetris.domain.model.Tetromino
import com.texttetris.domain.model.toComposeColor
import com.texttetris.ui.theme.*

@Composable
fun NextPiecePreview(
    tetromino: Tetromino?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(70.dp)
            .background(Surface)
            .border(
                width = 1.dp,
                color = NeonPurple,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(2.dp)
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            tetromino?.let { piece ->
                val cellSize = 16f
                val offsetX = (size.width - piece.type.width * cellSize) / 2
                val offsetY = (size.height - piece.type.height * cellSize) / 2
                val pieceColor = piece.type.color.toComposeColor()

                piece.blocks.forEach { pos ->
                    // Glow
                    drawRect(
                        color = pieceColor.copy(alpha = 0.4f),
                        topLeft = Offset(
                            offsetX + pos.x * cellSize - 2,
                            offsetY + pos.y * cellSize - 2
                        ),
                        size = Size(cellSize + 4, cellSize + 4)
                    )
                    // Main block
                    drawRect(
                        color = pieceColor,
                        topLeft = Offset(
                            offsetX + pos.x * cellSize,
                            offsetY + pos.y * cellSize
                        ),
                        size = Size(cellSize - 2, cellSize - 2)
                    )
                }
            }
        }
    }
}