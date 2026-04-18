package com.texttetris.ui.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.texttetris.data.local.GameDatabase
import com.texttetris.data.repository.GameRepositoryImpl
import com.texttetris.domain.model.GameState
import com.texttetris.util.SoundManager
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * 移除暂停功能的测试
 *
 * 验证游戏不再支持暂停和继续
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class RemovePauseFeatureTest {

    /**
     * 测试暂停功能已被移除
     *
     * 调用 pauseGame() 不应该改变游戏状态
     */
    @Test
    fun pauseGame_shouldNotChangeState() = runTest {
        val context = androidx.test.core.ApplicationProvider.getApplicationContext<android.content.Context>()
        val database = GameDatabase.getInstance(context)
        val repository = GameRepositoryImpl(database.gameRecordDao())
        val soundManager = SoundManager(context)
        val viewModel = GameViewModel(repository, soundManager)

        // Start game
        viewModel.startGame()

        // Verify game is playing
        assert(viewModel.gameState.value == GameState.PLAYING)

        // Try to pause - should remain PLAYING
        viewModel.pauseGame()
        assert(viewModel.gameState.value == GameState.PLAYING) // Should still be PLAYING
    }

    /**
     * 测试继续功能已被移除
     *
     * 调用 resumeGame() 不应该改变游戏状态
     */
    @Test
    fun resumeGame_shouldNotChangeState() = runTest {
        val context = androidx.test.core.ApplicationProvider.getApplicationContext<android.content.Context>()
        val database = GameDatabase.getInstance(context)
        val repository = GameRepositoryImpl(database.gameRecordDao())
        val soundManager = SoundManager(context)
        val viewModel = GameViewModel(repository, soundManager)

        // Start game
        viewModel.startGame()

        // Try to resume - should still be PLAYING
        viewModel.resumeGame()
        assert(viewModel.gameState.value == GameState.PLAYING)
    }
}
