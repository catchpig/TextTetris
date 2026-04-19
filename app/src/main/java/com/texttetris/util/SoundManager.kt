package com.texttetris.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

/**
 * 游戏音效管理器
 *
 * 使用 SoundPool 管理游戏音效播放
 * 支持：移动、旋转、落地、消行、游戏结束 5 种音效
 *
 * 音效资源放在 res/raw/ 目录下，文件名分别为：
 * move.wav、rotate.wav、drop.wav、clear.wav、gameover.wav
 *
 * @param context Android 上下文，用于加载音效资源
 */
class SoundManager(context: Context) {
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(4)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    private var moveSound: Int = 0
    private var rotateSound: Int = 0
    private var dropSound: Int = 0
    private var clearSound: Int = 0
    private var gameOverSound: Int = 0

    init {
        try {
            val resId = context.resources.getIdentifier("move", "raw", context.packageName)
            if (resId != 0) moveSound = soundPool.load(context, resId, 1)
            val rotateId = context.resources.getIdentifier("rotate", "raw", context.packageName)
            if (rotateId != 0) rotateSound = soundPool.load(context, rotateId, 1)
            val dropId = context.resources.getIdentifier("drop", "raw", context.packageName)
            if (dropId != 0) dropSound = soundPool.load(context, dropId, 1)
            val clearId = context.resources.getIdentifier("clear", "raw", context.packageName)
            if (clearId != 0) clearSound = soundPool.load(context, clearId, 1)
            val gameoverId = context.resources.getIdentifier("gameover", "raw", context.packageName)
            if (gameoverId != 0) gameOverSound = soundPool.load(context, gameoverId, 1)
        } catch (e: Exception) {
            // 音效资源不可用时继续运行（无声音效）
        }
    }

    /** 播放移动音效（左右移动） */
    fun playMove() { if (moveSound != 0) soundPool.play(moveSound, 0.5f, 0.5f, 1, 0, 1f) }
    /** 播放旋转音效 */
    fun playRotate() { if (rotateSound != 0) soundPool.play(rotateSound, 0.3f, 0.3f, 1, 0, 1f) }
    /** 播放落地音效（直接落地） */
    fun playDrop() { if (dropSound != 0) soundPool.play(dropSound, 0.6f, 0.6f, 1, 0, 1f) }
    /** 播放消行音效 */
    fun playClear() { if (clearSound != 0) soundPool.play(clearSound, 1.0f, 1.0f, 1, 0, 1f) }
    /** 播放游戏结束音效 */
    fun playGameOver() { if (gameOverSound != 0) soundPool.play(gameOverSound, 1f, 1f, 1, 0, 0.8f) }

    /** 释放 SoundPool 资源（ViewModel 清除时调用） */
    fun release() { soundPool.release() }
}
