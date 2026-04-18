package com.texttetris.domain.usecase

import com.texttetris.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class SpawnTetrominoUseCaseTest {

    private val useCase = SpawnTetrominoUseCase()

    @Test
    fun `spawn creates tetromino at starting position`() {
        val tetromino = useCase.spawn()

        assertEquals(Position(3, 0), tetromino.position)
    }

    @Test
    fun `spawn creates tetromino with rotation 0`() {
        val tetromino = useCase.spawn()

        assertEquals(0, tetromino.rotation)
    }

    @Test
    fun `spawn creates random tetromino type`() {
        // Spawn multiple times, should get variety
        val types = mutableSetOf<TetrominoType>()
        repeat(20) {
            types.add(useCase.spawn().type)
        }

        // Should have multiple different types in 20 spawns
        assertTrue(types.size > 1)
    }

    @Test
    fun `spawn with specific type creates that type`() {
        val tetromino = useCase.spawn(TetrominoType.I)

        assertEquals(TetrominoType.I, tetromino.type)
    }

    @Test
    fun `spawn with specific type respects position`() {
        val tetromino = useCase.spawn(TetrominoType.O)

        assertEquals(Position(3, 0), tetromino.position)
    }

    @Test
    fun `spawn all types are valid`() {
        TetrominoType.entries.forEach { type ->
            val tetromino = useCase.spawn(type)
            assertEquals(type, tetromino.type)
        }
    }

    @Test
    fun `spawned tetromino blocks are within board bounds`() {
        val tetromino = useCase.spawn()
        val board = GameBoard.create()

        tetromino.blocks.forEach { relPos ->
            val absX = tetromino.position.x + relPos.x
            val absY = tetromino.position.y + relPos.y
            assertTrue(absX in 0 until board.width)
            assertTrue(absY in 0 until board.height)
        }
    }
}
