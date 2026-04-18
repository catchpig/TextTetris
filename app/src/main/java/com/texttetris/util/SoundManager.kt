package com.texttetris.util

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

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
            // Sound resources not available - continue without sound
        }
    }

    fun playMove() { if (moveSound != 0) soundPool.play(moveSound, 0.5f, 0.5f, 1, 0, 1f) }
    fun playRotate() { if (rotateSound != 0) soundPool.play(rotateSound, 0.3f, 0.3f, 1, 0, 1f) }
    fun playDrop() { if (dropSound != 0) soundPool.play(dropSound, 0.6f, 0.6f, 1, 0, 1f) }
    fun playClear() { if (clearSound != 0) soundPool.play(clearSound, 0.7f, 0.7f, 1, 0, 1f) }
    fun playGameOver() { if (gameOverSound != 0) soundPool.play(gameOverSound, 1f, 1f, 1, 0, 0.8f) }

    fun release() { soundPool.release() }
}
