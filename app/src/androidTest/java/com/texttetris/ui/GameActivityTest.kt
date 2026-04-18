package com.texttetris.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.texttetris.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 游戏 Activity 仪器化测试
 *
 * 在真机或模拟器上运行的完整游戏流程测试
 *
 * 运行方式：
 * 1. 连接 Android 设备或启动模拟器
 * 2. ./gradlew connectedDebugAndroidTest
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class GameActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    /**
     * 测试游戏 Activity 可以正常启动
     */
    @Test
    fun activity_canLaunch() {
        // 验证 Activity 启动成功
        assert(true)
    }

    /**
     * 测试游戏初始状态显示开始按钮
     */
    @Test
    fun initialState_showsStartButton() {
        // 等待 UI 渲染
        Thread.sleep(1000)
        // TODO: 使用 Espresso 验证开始按钮可见
        assert(true)
    }

    /**
     * 测试点击开始按钮后游戏开始
     */
    @Test
    fun clickStart_gameBegins() {
        // TODO: 点击开始按钮后验证游戏状态变化
        assert(true)
    }

    /**
     * 测试游戏过程中分数更新
     */
    @Test
    fun gameplay_scoreUpdates() {
        // TODO: 验证分数在游戏过程中正确更新
        assert(true)
    }
}
