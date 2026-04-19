package com.texttetris

import android.app.Application
import com.texttetris.util.SoundManager

/**
 * 游戏应用类
 *
 * 全局单例，提供应用级依赖
 * 持有 SoundManager 实例供全局使用
 */
class TextTetrisApp : Application() {
    lateinit var soundManager: SoundManager
        private set

    override fun onCreate() {
        super.onCreate()
        soundManager = SoundManager(this)
    }
}
