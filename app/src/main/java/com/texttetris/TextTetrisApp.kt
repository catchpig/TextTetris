package com.texttetris

import android.app.Application
import com.texttetris.util.SoundManager

class TextTetrisApp : Application() {
    lateinit var soundManager: SoundManager
        private set

    override fun onCreate() {
        super.onCreate()
        soundManager = SoundManager(this)
    }
}
