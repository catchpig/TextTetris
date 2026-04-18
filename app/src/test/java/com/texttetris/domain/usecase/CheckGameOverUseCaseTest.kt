package com.texttetris.domain.usecase

import com.texttetris.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class CheckGameOverUseCaseTest {

    private val useCase = CheckGameOverUseCase()
    private val emptyBoard = GameBoard.create()

    @Test
    fun `isGameOver returns false when board is empty`() {
        val tetromino = Tetromino.create(TetrominoType.T, position = Position(5, 0))

        assertFalse(useCase.isGameOver(tetromino, emptyBoard))
    }

    @Test
    fun `isGameOver returns true when spawn position is occupied`() {
        // T-piece at (5, 0) has blocks at (6,0), (5,1), (6,1), (7,1)
        // If we block at (6,0), the piece cannot be placed
        val blockedBoard = emptyBoard.setCell(pos = Position(6, 0), color = TetrominoColor.CYAN)
        val tetromino = Tetromino.create(TetrominoType.T, position = Position(5, 0))

        assertTrue(useCase.isGameOver(tetromino, blockedBoard))
    }

    @Test
    fun `isGameOver returns true when any block position is occupied`() {
        // T-piece at (5, 0) has blocks at (6,0), (5,1), (6,1), (7,1)
        // Blocking at (5,1) which is one of T's blocks
        val blockedBoard = emptyBoard.setCell(pos = Position(5, 1), color = TetrominoColor.CYAN)
        val tetromino = Tetromino.create(TetrominoType.T, position = Position(5, 0))

        assertTrue(useCase.isGameOver(tetromino, blockedBoard))
    }

    @Test
    fun `isGameOver returns false when piece can be placed`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))

        assertFalse(useCase.isGameOver(tetromino, emptyBoard))
    }

    @Test
    fun `isGameOver returns true when piece is blocked by existing blocks`() {
        // I-piece at (5, 17) has blocks at (5,18), (6,18), (7,18), (8,18) for rotation 0
        // Block at (5,18) will prevent placement
        val blockedBoard = emptyBoard.setCell(pos = Position(5, 18), color = TetrominoColor.CYAN)
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 17))

        assertTrue(useCase.isGameOver(tetromino, blockedBoard))
    }

    @Test
    fun `isGameOver returns false when piece at bottom has room to fall`() {
        // I-piece at (5, 18) is at the bottom row
        // Even though blocks at (5,19), etc. would be outside board, the piece can still be placed
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 18))

        assertFalse(useCase.isGameOver(tetromino, emptyBoard))
    }
}
