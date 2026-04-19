package com.texttetris.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * 赛博朋克主题颜色定义
 *
 * 定义游戏界面使用的所有颜色
 */

// region 背景色
/** 主背景色 - 深蓝黑 */
val Background = Color(0xFF050510)
/** 深色背景渐变 */
val BackgroundDark = Color(0xFF0A0A1A)
/** 表面/卡片背景色 */
val Surface = Color(0xFF12122A)
/** 浅色表面背景 */
val SurfaceLight = Color(0xFF1A1A3A)
// endregion

// region 霓虹色（主色）
/** 霓虹青色 - 主要强调色 */
val NeonCyan = Color(0xFF00FFFF)
/** 霓虹青色发光效果 */
val NeonCyanGlow = Color(0xFF00FFFF).copy(alpha = 0.5f)
/** 霓虹黄色 */
val NeonYellow = Color(0xFFFFFF00)
/** 霓虹黄色发光效果 */
val NeonYellowGlow = Color(0xFFFFFF00).copy(alpha = 0.5f)
/** 霓虹紫色 */
val NeonPurple = Color(0xFF8000FF)
/** 霓虹紫色发光效果 */
val NeonPurpleGlow = Color(0xFF8000FF).copy(alpha = 0.5f)
/** 霓虹绿色 */
val NeonGreen = Color(0xFF00FF00)
/** 霓虹绿色发光效果 */
val NeonGreenGlow = Color(0xFF00FF00).copy(alpha = 0.5f)
/** 霓虹红色 */
val NeonRed = Color(0xFFFF0040)
/** 霓虹红色发光效果 */
val NeonRedGlow = Color(0xFFFF0040).copy(alpha = 0.5f)
/** 霓虹蓝色 */
val NeonBlue = Color(0xFF0080FF)
/** 霓虹橙色 */
val NeonOrange = Color(0xFFFF8000)
// endregion

// region 网格和线条色
/** 网格线颜色 */
val GridLineColor = Color(0xFF1A1A4A)
/** 高亮网格线发光色 */
val GlowLineColor = Color(0xFF00FFFF).copy(alpha = 0.15f)
// endregion

// region 文字色
/** 主要文字颜色 */
val TextPrimary = Color(0xFFE0E0FF)
/** 次要文字颜色 */
val TextSecondary = Color(0xFF8080A0)
/** 文字发光效果色 */
val TextGlow = Color(0xFF00FFFF).copy(alpha = 0.8f)
// endregion
