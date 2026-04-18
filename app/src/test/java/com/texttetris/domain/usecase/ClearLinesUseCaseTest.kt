package com.texttetris.domain.usecase

import com.texttetris.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class ClearLinesUseCaseTest {

    private val useCase = ClearLinesUseCase()

    @Test
    fun `clear returns same board when no lines are full`() {
        val board = GameBoard.create()
            .setCell(pos = Position(5, 10), color = TetrominoColor.CYAN)

        val (newBoard, lines) = useCase.clear(board)

        assertEquals(0, lines)
        assertNotNull(newBoard.cells[10][5].color)
    }

    @Test
    fun `clear removes single full line`() {
        var board = GameBoard.create()
        // Fill row 15 completely
        repeat(10) { x ->
            board = board.setCell(pos = Position(x, 15), color = TetrominoColor.CYAN)
        }

        val (newBoard, lines) = useCase.clear(board)

        assertEquals(1, lines)
        // Row 15 should now be empty (was row 16, shifted down then row 15 was added as empty at top)
        // Actually after clear, what was row 16 is now at row 15, and row 0 is empty at top
        assertNull(newBoard.cells[15][0].color)
    }

    @Test
    fun `clear removes multiple full lines`() {
        var board = GameBoard.create()
        // Fill rows 15 and 16 completely
        repeat(10) { x ->
            board = board.setCell(pos = Position(x, 15), color = TetrominoColor.CYAN)
            board = board.setCell(pos = Position(x, 16), color = TetrominoColor.CYAN)
        }

        val (newBoard, lines) = useCase.clear(board)

        assertEquals(2, lines)
        assertNull(newBoard.cells[15][0].color)
        assertNull(newBoard.cells[16][0].color)
    }

    @Test
    fun `clear removes four full lines (Tetris)`() {
        var board = GameBoard.create()
        // Fill rows 12, 13, 14, 15 completely (I-piece Tetris)
        repeat(10) { x ->
            board = board.setCell(pos = Position(x, 12), color = TetrominoColor.CYAN)
            board = board.setCell(pos = Position(x, 13), color = TetrominoColor.CYAN)
            board = board.setCell(pos = Position(x, 14), color = TetrominoColor.CYAN)
            board = board.setCell(pos = Position(x, 15), color = TetrominoColor.CYAN)
        }

        val (newBoard, lines) = useCase.clear(board)

        assertEquals(4, lines)
    }

    @Test
    fun `clear shifts rows down after clearing`() {
        var board = GameBoard.create()
        // Fill row 15 completely
        repeat(10) { x ->
            board = board.setCell(pos = Position(x, 15), color = TetrominoColor.CYAN)
        }
        // Place a block at row 14, column 5
        board = board.setCell(pos = Position(5, 14), color = TetrominoColor.RED)

        val (newBoard, _) = useCase.clear(board)

        // After clearing row 15, the remaining rows shift down
        // Original row 14 moves to row 15
        // So the RED block that was at (5, 14) is now at (5, 15)
        assertEquals(TetrominoColor.RED, newBoard.cells[15][5].color)
        // Original row 15 (the cleared row) should be empty at the top
        assertNull(newBoard.cells[0][5].color)
    }

    @Test
    fun `clear multiple lines shifts correctly`() {
        var board = GameBoard.create()
        // Fill rows 17 and 18 completely
        repeat(10) { x ->
            board = board.setCell(pos = Position(x, 17), color = TetrominoColor.CYAN)
            board = board.setCell(pos = Position(x, 18), color = TetrominoColor.CYAN)
        }
        // Place a block at row 16
        board = board.setCell(pos = Position(5, 16), color = TetrominoColor.RED)

        val (newBoard, _) = useCase.clear(board)

        // After clearing rows 17 and 18:
        // Rows 0-16 remain, rows 17-18 are removed, 2 empty rows added at top
        // Original row 16 moves to row 18 (16 + 2 = 18)
        assertEquals(TetrominoColor.RED, newBoard.cells[18][5].color)
    }
}
