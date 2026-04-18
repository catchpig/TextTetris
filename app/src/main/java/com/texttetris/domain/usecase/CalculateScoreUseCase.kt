package com.texttetris.domain.usecase

import com.texttetris.domain.model.Score

/**
 * 计算分数用例
 *
 * 负责计算游戏得分和等级提升
 */
class CalculateScoreUseCase {

    /**
     * 增加分数
     *
     * 根据消除的行数和当前等级计算新分数
     *
     * @param currentScore 当前分数
     * @param linesCleared 本次消除的行数
     * @param level 当前等级
     * @return 新分数
     */
    fun addScore(currentScore: Int, linesCleared: Int, level: Int): Int {
        return currentScore + Score.calculate(linesCleared, level)
    }

    /**
     * 等级提升
     *
     * 根据总消除行数计算是否需要升级
     *
     * @param totalLinesCleared 总消除行数
     * @param currentLevel 当前等级
     * @return 新等级
     */
    fun levelUp(totalLinesCleared: Int, currentLevel: Int): Int {
        return Score.levelUp(totalLinesCleared, currentLevel)
    }
}