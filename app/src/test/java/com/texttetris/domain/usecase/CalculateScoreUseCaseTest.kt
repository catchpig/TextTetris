package com.texttetris.domain.usecase

import org.junit.Assert.*
import org.junit.Test

class CalculateScoreUseCaseTest {

    private val useCase = CalculateScoreUseCase()

    @Test
    fun `addScore returns correct points for single line`() {
        val score = useCase.addScore(currentScore = 0, linesCleared = 1, level = 1)
        assertEquals(100, score)
    }

    @Test
    fun `addScore returns correct points for double line`() {
        val score = useCase.addScore(currentScore = 0, linesCleared = 2, level = 1)
        assertEquals(300, score)
    }

    @Test
    fun `addScore returns correct points for triple line`() {
        val score = useCase.addScore(currentScore = 0, linesCleared = 3, level = 1)
        assertEquals(500, score)
    }

    @Test
    fun `addScore returns correct points for Tetris (4 lines)`() {
        val score = useCase.addScore(currentScore = 0, linesCleared = 4, level = 1)
        assertEquals(800, score)
    }

    @Test
    fun `addScore multiplies by level`() {
        assertEquals(200, useCase.addScore(0, 1, 2)) // 100 * 2
        assertEquals(400, useCase.addScore(0, 1, 4)) // 100 * 4
        assertEquals(1600, useCase.addScore(0, 4, 2)) // 800 * 2
    }

    @Test
    fun `addScore accumulates with current score`() {
        val score = useCase.addScore(currentScore = 500, linesCleared = 1, level = 1)
        assertEquals(600, score)
    }

    @Test
    fun `addScore handles unknown line count`() {
        // 5+ lines uses fallback calculation
        val score = useCase.addScore(currentScore = 0, linesCleared = 5, level = 1)
        assertEquals(500, score) // 5 * 100 * 1
    }

    @Test
    fun `levelUp increases level every 10 lines`() {
        assertEquals(1, useCase.levelUp(totalLinesCleared = 0, currentLevel = 1))
        assertEquals(2, useCase.levelUp(totalLinesCleared = 10, currentLevel = 1))
        assertEquals(3, useCase.levelUp(totalLinesCleared = 20, currentLevel = 1))
        assertEquals(5, useCase.levelUp(totalLinesCleared = 40, currentLevel = 1))
    }

    @Test
    fun `levelUp does not increase below 10 lines`() {
        assertEquals(1, useCase.levelUp(totalLinesCleared = 9, currentLevel = 1))
    }

    @Test
    fun `levelUp carries over existing level`() {
        assertEquals(5, useCase.levelUp(totalLinesCleared = 10, currentLevel = 4))
        assertEquals(6, useCase.levelUp(totalLinesCleared = 20, currentLevel = 4))
    }

    @Test
    fun `levelUp handles zero current level`() {
        assertEquals(1, useCase.levelUp(totalLinesCleared = 10, currentLevel = 0))
    }
}
