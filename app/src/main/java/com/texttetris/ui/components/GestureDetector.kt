package com.texttetris.ui.components

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * 手势检测常量
 */
private const val SWIPE_THRESHOLD = 30f       // 降低阈值提高灵敏度
private const val VELOCITY_THRESHOLD = 0.5f  // 速度阈值
private const val DRAG_THRESHOLD = 10f       // 拖拽启动阈值

/**
 * 手势检测修饰符
 *
 * 支持：
 * - 滑动手势（左/右/上/下）
 * - 拖拽移动
 * - 双击（已禁用）
 */
fun Modifier.gestureDetector(
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    onSwipeUp: () -> Unit,
    onSwipeDown: () -> Unit,
    onDoubleTap: () -> Unit,
    onDragMove: ((Float) -> Unit)? = null
): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            val down = awaitFirstDown(requireUnconsumed = true)
            val startPos = down.position
            var hasSwiped = false
            var lastDragY = startPos.y
            val startTime = System.currentTimeMillis()

            // 用于计算速度
            var lastPos = startPos
            var lastTime = startTime

            do {
                val event = awaitPointerEvent()
                val change = event.changes.firstOrNull() ?: break
                val currentPos = change.position
                val currentTime = System.currentTimeMillis()

                // 计算位移和时间差
                val delta = currentPos - startPos
                val deltaX = delta.x
                val deltaY = delta.y
                val timeDelta = currentTime - lastTime

                // 计算速度（如果时间差 > 0）
                val velocityX = if (timeDelta > 0) (currentPos.x - lastPos.x) / timeDelta else 0f
                val velocityY = if (timeDelta > 0) (currentPos.y - lastPos.y) / timeDelta else 0f

                // 计算从开始位置的总位移
                val totalDeltaX = currentPos.x - startPos.x
                val totalDeltaY = currentPos.y - startPos.y
                val distance = sqrt(totalDeltaX * totalDeltaX + totalDeltaY * totalDeltaY)

                // 主方向判断：水平 vs 垂直
                val isHorizontal = abs(totalDeltaX) > abs(totalDeltaY)

                // 滑动手势检测（只在尚未检测到滑动时）
                if (!hasSwiped && distance > SWIPE_THRESHOLD) {
                    when {
                        // 水平滑动优先（左右）
                        isHorizontal && abs(totalDeltaX) > abs(totalDeltaY) * 1.2 -> {
                            hasSwiped = true
                            if (totalDeltaX > 0) {
                                onSwipeRight()
                            } else {
                                onSwipeLeft()
                            }
                        }
                        // 垂直滑动（上下）
                        !isHorizontal && abs(totalDeltaY) > abs(totalDeltaX) * 1.2 -> {
                            hasSwiped = true
                            if (totalDeltaY > 0) {
                                onSwipeDown()
                            } else {
                                onSwipeUp()
                            }
                        }
                    }
                }

                // 连续拖拽检测（用于跟随手指移动）
                if (hasSwiped && onDragMove != null && event.changes.any { it.pressed }) {
                    val dragDelta = currentPos.y - lastDragY
                    if (abs(dragDelta) > 2f) { // 降低敏感度，只在有明显移动时触发
                        onDragMove(dragDelta)
                        lastDragY = currentPos.y
                    }
                }

                lastPos = currentPos
                lastTime = currentTime

                change.consume()
            } while (event.changes.any { it.pressed })
        }
    }
}
