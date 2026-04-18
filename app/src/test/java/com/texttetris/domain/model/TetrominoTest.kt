package com.texttetris.domain.model

import org.junit.Assert.*
import org.junit.Test

class TetrominoTest {

    @Test
    fun `I piece has correct shape and color`() {
        val tetromino = Tetromino.create(TetrominoType.I)

        assertEquals(TetrominoType.I, tetromino.type)
        assertEquals(TetrominoColor.CYAN, tetromino.type.color)
        assertEquals(4, tetromino.blocks.size)
    }

    @Test
    fun `O piece has correct shape`() {
        val tetromino = Tetromino.create(TetrominoType.O)

        assertEquals(TetrominoType.O, tetromino.type)
        assertEquals(4, tetromino.blocks.size)
    }

    @Test
    fun `absoluteBlocks returns positions relative to piece position`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(3, 5))

        val absolutePositions = tetromino.absoluteBlocks()

        assertEquals(4, absolutePositions.size)
        // I piece at rotation 0: blocks at (0,1), (1,1), (2,1), (3,1)
        // With position (3,5): absolute = (3,6), (4,6), (5,6), (6,6)
        assertTrue(absolutePositions.any { it.x == 3 && it.y == 6 })
        assertTrue(absolutePositions.any { it.x == 4 && it.y == 6 })
        assertTrue(absolutePositions.any { it.x == 5 && it.y == 6 })
        assertTrue(absolutePositions.any { it.x == 6 && it.y == 6 })
    }

    @Test
    fun `moveTo updates position correctly`() {
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(3, 5))
        val moved = tetromino.moveTo(Position(4, 7))

        assertEquals(Position(4, 7), moved.position)
    }

    @Test
    fun `rotate rotates clockwise`() {
        val tetromino = Tetromino.create(TetrominoType.T, position = Position(0, 0))

        val rotated = tetromino.rotate()

        assertNotEquals(tetromino.blocks, rotated.blocks)
        assertEquals(1, rotated.rotation)
    }

    @Test
    fun `TetrominoType I has width 4 and height 1`() {
        assertEquals(4, TetrominoType.I.width)
        assertEquals(1, TetrominoType.I.height)
    }

    @Test
    fun `TetrominoType O has width 2 and height 2`() {
        assertEquals(2, TetrominoType.O.width)
        assertEquals(2, TetrominoType.O.height)
    }

    @Test
    fun `all TetrominoTypes have valid dimensions`() {
        TetrominoType.entries.forEach { type ->
            assertTrue(type.name, type.width > 0)
            assertTrue(type.name, type.height > 0)
        }
    }
}
