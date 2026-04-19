package com.texttetris.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * TextTetris 应用主题
 *
 * 使用深色赛博朋克风格配色
 */
private val DarkColorScheme = darkColorScheme(
    background = Background,
    surface = Surface,
    primary = NeonCyan,
    secondary = NeonPurple
)

@Composable
fun TextTetrisTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
