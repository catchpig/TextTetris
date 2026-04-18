package com.texttetris.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.texttetris.domain.model.Tetromino
import com.texttetris.ui.theme.*
import com.texttetris.R

/**
 * 计分板显示组件
 *
 * 显示当前分数、等级、最高分
 *
 * @param score 当前游戏分数
 * @param level 当前等级
 * @param linesCleared 已消除的行数
 * @param highScore 历史最高分
 * @param nextTetromino 下一个方块预览
 * @param showControlHints 是否显示控制提示
 * @param modifier Compose 修饰符
 */
@Composable
fun ScoreDisplay(
    score: Int,
    level: Int,
    linesCleared: Int,
    highScore: Int,
    nextTetromino: Tetromino? = null,
    showControlHints: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Surface.copy(alpha = 0.8f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .border(
                1.dp,
                NeonCyan.copy(alpha = 0.3f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 当前分数
            Column {
                Text(
                    text = stringResource(R.string.label_score),
                    color = TextSecondary,
                    fontSize = 10.sp
                )
                Text(
                    text = score.toString().padStart(6, '0'),
                    color = NeonCyan,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 当前等级
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.label_level),
                    color = TextSecondary,
                    fontSize = 10.sp
                )
                Text(
                    text = level.toString(),
                    color = NeonPurple,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 最高分
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(R.string.label_high),
                    color = TextSecondary,
                    fontSize = 10.sp
                )
                Text(
                    text = highScore.toString().padStart(6, '0'),
                    color = NeonYellow,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 下一个方块预览
            if (nextTetromino != null) {
                Spacer(modifier = Modifier.width(8.dp))
                NextPiecePreview(tetromino = nextTetromino)
            }
        }

        // 控制提示在左下角
        if (showControlHints) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.control_hints),
                color = TextSecondary,
                fontSize = 10.sp
            )
        }
    }
}
