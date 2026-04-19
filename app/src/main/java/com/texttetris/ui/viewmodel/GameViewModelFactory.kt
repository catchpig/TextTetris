package com.texttetris.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.texttetris.data.local.GameDatabase
import com.texttetris.data.repository.GameRepositoryImpl
import com.texttetris.domain.repository.GameRepository
import com.texttetris.util.SoundManager

/**
 * GameViewModel 工厂类
 *
 * 负责创建 GameViewModel 实例，并注入所需依赖
 * - GameDatabase 单例
 * - GameRepository 实现
 * - SoundManager 实例
 *
 * @param context Android 上下文，用于创建依赖
 */
class GameViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = GameDatabase.getInstance(context)
        val repository: GameRepository = GameRepositoryImpl(database.gameRecordDao())
        val soundManager = SoundManager(context)
        return GameViewModel(repository, soundManager) as T
    }
}