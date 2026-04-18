package com.texttetris.domain.model

import org.junit.Assert.*
import org.junit.Test

class GameBoardTest {

    @Test
    fun `create board with correct dimensions`() {
        val board = GameBoard(width = 10, height = 20)

        assertEquals(10, board.width)
        assertEquals(20, board.height)
        assertEquals(20, board.cells.size)
        assertEquals(10, board.cells[0].size)
    }

    @Test
    fun `setCell returns new board with updated cell`() {
        val board = GameBoard(width = 10, height = 20)
        val color = TetrominoColor.CYAN

        val newBoard = board.setCell(pos = Position(5, 10), color = color)

        assertEquals(color, newBoard.cells[10][5].color)
        // Original board unchanged
        assertNull(board.cells[10][5].color)
    }

    @Test
    fun `clearFullLines clears full row and returns count`() {
        val board = GameBoard(width = 10, height = 20)
        var filledBoard = board
        repeat(10) { x ->
            filledBoard = filledBoard.setCell(pos = Position(x, 15), color = TetrominoColor.CYAN)
        }

        val (newBoard, lines) = filledBoard.clearFullLines()

        assertEquals(1, lines)
        assertNull(newBoard.cells[15][0].color)
    }

    @Test
    fun `isValidPosition returns true for valid positions`() {
        val board = GameBoard(width = 10, height = 20)

        assertTrue(board.isValidPosition(Position(0, 0)))
        assertTrue(board.isValidPosition(Position(9, 19)))
        assertTrue(board.isValidPosition(Position(5, 10)))
    }

    @Test
    fun `isValidPosition returns false for invalid positions`() {
        val board = GameBoard(width = 10, height = 20)

        assertFalse(board.isValidPosition(Position(-1, 0)))
        assertFalse(board.isValidPosition(Position(0, -1)))
        assertFalse(board.isValidPosition(Position(10, 0)))
        assertFalse(board.isValidPosition(Position(0, 20)))
    }

    @Test
    fun `isEmptyPosition returns true when cell is empty`() {
        val board = GameBoard(width = 10, height = 20)

        assertTrue(board.isEmptyPosition(Position(5, 10)))
    }

    @Test
    fun `isEmptyPosition returns false when cell is filled`() {
        val board = GameBoard(width = 10, height = 20)
        val filledBoard = board.setCell(pos = Position(5, 10), color = TetrominoColor.CYAN)

        assertFalse(filledBoard.isEmptyPosition(Position(5, 10)))
    }

    @Test
    fun `clearFullLines returns zero when no lines are full`() {
        val board = GameBoard(width = 10, height = 20)
        val boardWithCell = board.setCell(pos = Position(5, 10), color = TetrominoColor.CYAN)

        val (_, lines) = boardWithCell.clearFullLines()

        assertEquals(0, lines)
    }
}
