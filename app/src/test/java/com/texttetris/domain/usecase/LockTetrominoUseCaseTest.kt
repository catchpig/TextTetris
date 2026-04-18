package com.texttetris.domain.usecase

import com.texttetris.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class LockTetrominoUseCaseTest {

    private val useCase = LockTetrominoUseCase()
    private val emptyBoard = GameBoard.create()

    @Test
    fun `lock places tetromino blocks on board`() {
        // T-piece at default position (3, 0) has blocks:
        // (1,0), (0,1), (1,1), (2,1) relative
        // Absolute at (4,0), (3,1), (4,1), (5,1)
        val tetromino = Tetromino.create(TetrominoType.T, position = Position(3, 0))

        val board = useCase.lock(tetromino, emptyBoard)

        // Verify all blocks are placed
        val expectedPositions = tetromino.absoluteBlocks()
        expectedPositions.forEach { pos ->
            assertEquals("Block at $pos should be ${tetromino.type.color}",
                tetromino.type.color, board.cells[pos.y][pos.x].color)
        }
    }

    @Test
    fun `lock does not modify original board`() {
        val tetromino = Tetromino.create(TetrominoType.T, position = Position(5, 10))

        useCase.lock(tetromino, emptyBoard)

        // Original board should be empty
        assertNull(emptyBoard.cells[10][5].color)
    }

    @Test
    fun `lock I piece horizontal`() {
        // I-piece horizontal at (0, 10) has blocks at (0,11), (1,11), (2,11), (3,11)
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(0, 10))

        val board = useCase.lock(tetromino, emptyBoard)

        assertEquals(TetrominoColor.CYAN, board.cells[11][0].color)
        assertEquals(TetrominoColor.CYAN, board.cells[11][1].color)
        assertEquals(TetrominoColor.CYAN, board.cells[11][2].color)
        assertEquals(TetrominoColor.CYAN, board.cells[11][3].color)
    }

    @Test
    fun `lock O piece`() {
        // O-piece at (3, 0) has blocks at (4,0), (5,0), (4,1), (5,1)
        val tetromino = Tetromino.create(TetrominoType.O, position = Position(3, 0))

        val board = useCase.lock(tetromino, emptyBoard)

        assertEquals(TetrominoColor.YELLOW, board.cells[0][4].color)
        assertEquals(TetrominoColor.YELLOW, board.cells[0][5].color)
        assertEquals(TetrominoColor.YELLOW, board.cells[1][4].color)
        assertEquals(TetrominoColor.YELLOW, board.cells[1][5].color)
    }

    @Test
    fun `lock multiple pieces accumulates on board`() {
        val tetromino1 = Tetromino.create(TetrominoType.T, position = Position(3, 0))
        val boardAfterFirst = useCase.lock(tetromino1, emptyBoard)

        val tetromino2 = Tetromino.create(TetrominoType.I, position = Position(0, 10))
        val finalBoard = useCase.lock(tetromino2, boardAfterFirst)

        // T-piece blocks should still be there
        tetromino1.absoluteBlocks().forEach { pos ->
            assertEquals(tetromino1.type.color, finalBoard.cells[pos.y][pos.x].color)
        }
        // I-piece blocks should be there
        tetromino2.absoluteBlocks().forEach { pos ->
            assertEquals(tetromino2.type.color, finalBoard.cells[pos.y][pos.x].color)
        }
    }

    @Test
    fun `lock rotated tetromino places blocks correctly`() {
        // I-piece at (5, 10) rotated once (vertical)
        // Use rotate() to get proper blocks for the rotation
        val tetromino = Tetromino.create(TetrominoType.I, position = Position(5, 10))
        val rotated = tetromino.rotate()

        val board = useCase.lock(rotated, emptyBoard)

        // I vertical at (5,10) rotation 1 has blocks at:
        // (2,0), (2,1), (2,2), (2,3) relative -> (7,10), (7,11), (7,12), (7,13) absolute
        assertEquals(TetrominoColor.CYAN, board.cells[10][7].color)
        assertEquals(TetrominoColor.CYAN, board.cells[11][7].color)
        assertEquals(TetrominoColor.CYAN, board.cells[12][7].color)
        assertEquals(TetrominoColor.CYAN, board.cells[13][7].color)
    }
}
