package com.texttetris.util

/**
 * 游戏日志工具类
 *
 * 统一管理游戏日志输出，使用 Android Log 类
 *
 * 日志级别：
 * - DEBUG: 移动、旋转等频繁操作
 * - INFO: 游戏状态变化（开始、暂停、继续）
 * - WARN: 异常情况（碰撞检测失败、生成位置冲突）
 * - ERROR: 游戏结束等严重情况
 *
 * 上线前可通过设置 [isEnabled] 为 false 关闭所有日志
 *
 * 注意：此类使用反射检测 Android 环境，确保单元测试（JVM）可以正常运行
 */
object GameLogger {

    /** 日志标签 */
    private const val TAG = "TextTetris"

    /** 是否启用日志（上线前可设为 false） */
    var isEnabled: Boolean = true

    /** 是否运行在 Android 平台（通过反射检测） */
    private val isAndroid: Boolean by lazy {
        try {
            Class.forName("android.util.Log")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    /**
     * 调试级别日志
     *
     * 用于频繁操作：移动、旋转、碰撞检测等
     */
    fun d(message: String, tag: String = TAG) {
        if (isEnabled && isAndroid) {
            try {
                android.util.Log.d(tag, message)
            } catch (e: Throwable) {
                // 忽略 Android 特定的错误
            }
        }
    }

    /**
     * 信息级别日志
     *
     * 用于游戏状态变化：开始、暂停、继续、消行等
     */
    fun i(message: String, tag: String = TAG) {
        if (isEnabled && isAndroid) {
            try {
                android.util.Log.i(tag, message)
            } catch (e: Throwable) {
                // 忽略 Android 特定的错误
            }
        }
    }

    /**
     * 警告级别日志
     *
     * 用于异常情况：生成位置冲突、碰撞检测失败等
     */
    fun w(message: String, tag: String = TAG) {
        if (isEnabled && isAndroid) {
            try {
                android.util.Log.w(tag, message)
            } catch (e: Throwable) {
                // 忽略 Android 特定的错误
            }
        }
    }

    /**
     * 错误级别日志
     *
     * 用于严重情况：游戏结束等
     */
    fun e(message: String, throwable: Throwable? = null, tag: String = TAG) {
        if (isEnabled && isAndroid) {
            try {
                if (throwable != null) {
                    android.util.Log.e(tag, message, throwable)
                } else {
                    android.util.Log.e(tag, message)
                }
            } catch (e: Throwable) {
                // 忽略 Android 特定的错误
            }
        }
    }

    /**
     * 详细调试日志（包含函数名和行号）
     *
     * 用于需要精确定位的调试场景
     */
    fun v(message: String, tag: String = TAG) {
        if (isEnabled && isAndroid) {
            try {
                android.util.Log.v(tag, message)
            } catch (e: Throwable) {
                // 忽略 Android 特定的错误
            }
        }
    }
}
