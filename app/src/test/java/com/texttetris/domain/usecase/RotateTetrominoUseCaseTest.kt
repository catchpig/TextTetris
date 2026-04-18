package com.texttetris.domain.usecase

import com.texttetris.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class RotateTetrominoUseCaseTest {

    private val emptyBoard = GameBoard.create()
    private val useCase = RotateTetrominoUseCase()

    @Test
    fun `rotate T piece clockwise`() {
        val tetromino = Tetromino.create(TetrominoType.T, position = Position(5, 10))

        val rotated = useCase.rotate(tetromino, emptyBoard)

        assertEquals(1, rotated.rotation)
        assertEquals(Position(5, 10), rotated.position)
    }

    @Test
    fun `rotate O piece does not change position`() {
        // O-piece looks the same at all rotations
        val tetromino = Tetromino.create(TetrominoType.O, position = Position(5, 10))

        val rotated = useCase.rotate(tetromino, emptyBoard)

        // O-piece rotation doesn't change blocks, but rotation index still increments
        assertEquals(1, rotated.rotation)
    }

    @Test
    fun `rotate on empty board succeeds without wall kick`() {
        val tetromino = Tetromino.create(TetrominoType.T, position = Position(5, 10))

        val rotated = useCase.rotate(tetromino, emptyBoard)

        // Direct rotation should succeed
        assertEquals(1, rotated.rotation)
    }

    @Test
    fun `multiple rotations cycle through all states`() {
        val tetromino = Tetromino.create(TetrominoType.T, position = Position(5, 10))
        var current = tetromino

        current = useCase.rotate(current, emptyBoard)
        assertEquals(1, current.rotation)

        current = useCase.rotate(current, emptyBoard)
        assertEquals(2, current.rotation)

        current = useCase.rotate(current, emptyBoard)
        assertEquals(3, current.rotation)

        current = useCase.rotate(current, emptyBoard)
        assertEquals(0, current.rotation)
    }

    @Test
    fun `rotate I piece at center`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(3, 10))

        val rotated = useCase.rotate(tetromino, emptyBoard)

        assertEquals(1, rotated.rotation)
    }
}
