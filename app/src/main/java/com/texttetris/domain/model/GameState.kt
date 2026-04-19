package com.texttetris.domain.model

/**
 * 游戏状态枚举
 *
 * 表示游戏的当前状态，用于控制 UI 显示和游戏逻辑
 *
 * - IDLE: 初始状态，显示开始按钮
 * - PLAYING: 游戏中，响应玩家输入
 * - PAUSED: 暂停状态（当前版本未使用 UI）
 * - GAME_OVER: 游戏结束，显示得分和重玩按钮
 */
enum class GameState {
    /** 初始状态，等待玩家开始 */
    IDLE,
    /** 游戏进行中 */
    PLAYING,
    /** 游戏暂停 */
    PAUSED,
    /** 游戏结束 */
    GAME_OVER
}
