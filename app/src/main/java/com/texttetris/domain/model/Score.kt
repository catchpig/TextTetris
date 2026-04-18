package com.texttetris.domain.model

/**
 * 计分系统
 *
 * 负责计算分数和等级提升
 */
object Score {
    /**
     * 消行基础分表
     *
     * 一次消除越多行，分数越高
     * 1行=100, 2行=300, 3行=500, 4行(Tetris)=800
     */
    private val SCORE_TABLE = mapOf(
        1 to 100,
        2 to 300,
        3 to 500,
        4 to 800
    )

    /**
     * 计算分数
     *
     * 分数 = 基础分 × 等级
     *
     * @param linesCleared 消除的行数
     * @param level 当前等级
     * @return 获得的分数
     */
    fun calculate(linesCleared: Int, level: Int): Int {
        val baseScore = SCORE_TABLE[linesCleared] ?: (linesCleared * 100)
        return baseScore * level
    }

    /**
     * 计算等级提升
     *
     * 每消除 10 行升一级
     *
     * @param linesCleared 总消除行数
     * @param currentLevel 当前等级
     * @return 新等级
     */
    fun levelUp(linesCleared: Int, currentLevel: Int): Int {
        val linesPerLevel = 10
        return currentLevel + (linesCleared / linesPerLevel)
    }
}
