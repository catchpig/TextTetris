package com.texttetris.domain.usecase

import com.texttetris.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class MoveTetrominoUseCaseTest {

    private val emptyBoard = GameBoard.create()
    private val useCase = MoveTetrominoUseCase()

    @Test
    fun `moveLeft moves tetromino left by one`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))

        val moved = useCase.moveLeft(tetromino, emptyBoard)

        assertEquals(Position(4, 10), moved.position)
    }

    @Test
    fun `moveRight moves tetromino right by one`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))

        val moved = useCase.moveRight(tetromino, emptyBoard)

        assertEquals(Position(6, 10), moved.position)
    }

    @Test
    fun `moveDown moves tetromino down by one`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))

        val moved = useCase.moveDown(tetromino, emptyBoard)

        assertEquals(Position(5, 11), moved.position)
    }

    @Test
    fun `canMoveDown returns true when space below is empty`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))

        assertTrue(useCase.canMoveDown(tetromino, emptyBoard))
    }

    @Test
    fun `canMoveDown returns false when at bottom`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 19))

        assertFalse(useCase.canMoveDown(tetromino, emptyBoard))
    }

    @Test
    fun `canMoveDown returns false when blocked by cell below`() {
        // I piece at (5, 10) has blocks at (5,11), (6,11), (7,11), (8,11)
        val filledBoard = emptyBoard.setCell(pos = Position(6, 12), color = TetrominoColor.CYAN)
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))

        assertFalse(useCase.canMoveDown(tetromino, filledBoard))
    }

    @Test
    fun `moveDownBy moves multiple rows`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))

        val moved = useCase.moveDownBy(tetromino, emptyBoard, 3)

        assertEquals(Position(5, 13), moved.position)
    }

    @Test
    fun `moveDownBy stops at obstacles`() {
        // I piece at (5, 10) blocks at (5,11), (6,11), (7,11), (8,11)
        // We block at (5,14) which blocks after move to (5,13) would place blocks at (5,14)
        val filledBoard = emptyBoard.setCell(pos = Position(5, 14), color = TetrominoColor.CYAN)
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))

        val moved = useCase.moveDownBy(tetromino, filledBoard, 5)

        // After 1st move: (5,11) - ok
        // After 2nd move: (5,12) - ok
        // After 3rd move: (5,13) - would place blocks at (5,14) which is blocked
        assertEquals(Position(5, 12), moved.position)
    }

    @Test
    fun `canPlace returns true when all blocks can be placed`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))

        assertTrue(useCase.canPlace(tetromino, emptyBoard))
    }

    @Test
    fun `canPlace returns false when any block overlaps`() {
        val filledBoard = emptyBoard.setCell(pos = Position(6, 11), color = TetrominoColor.CYAN)
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))

        assertFalse(useCase.canPlace(tetromino, filledBoard))
    }
}
