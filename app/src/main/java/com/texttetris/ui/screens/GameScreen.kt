package com.texttetris.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.texttetris.domain.model.GameState
import com.texttetris.domain.model.Tetromino
import com.texttetris.ui.components.*
import com.texttetris.ui.theme.*
import com.texttetris.ui.viewmodel.GameViewModel
import com.texttetris.ui.viewmodel.GameViewModelFactory
import com.texttetris.R

/**
 * 游戏主界面
 *
 * 完整的游戏界面，包含：
 * - 背景网格
 * - 计分板（分数、等级、最高分、下一个方块预览）
 * - 游戏棋盘
 * - 手势检测（滑动、拖拽）
 * - 游戏状态覆盖层（开始/结束）
 *
 * 自动适配横竖屏：竖屏时垂直布局，横屏时水平布局
 *
 * @param viewModel 游戏 ViewModel，用于状态管理
 */
@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(factory = GameViewModelFactory(LocalContext.current))
) {
    val gameState by viewModel.gameState.collectAsState()
    val board by viewModel.board.collectAsState()
    val currentTetromino by viewModel.currentTetromino.collectAsState()
    val nextTetromino by viewModel.nextTetromino.collectAsState()
    val score by viewModel.score.collectAsState()
    val level by viewModel.level.collectAsState()
    val linesCleared by viewModel.linesCleared.collectAsState()
    val highScore by viewModel.highScore.collectAsState()
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val configuration = LocalContext.current.resources.configuration
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BackgroundDark, Background, BackgroundDark)
                )
            )
            .then(
                if (gameState == GameState.PLAYING) {
                    Modifier.gestureDetector(
                        onSwipeLeft = viewModel::moveLeft,
                        onSwipeRight = viewModel::moveRight,
                        onSwipeUp = viewModel::rotate,
                        onSwipeDown = { },
                        onDoubleTap = { },
                        onDragMove = { delta ->
                            viewModel.onDragMove(delta, with(density) { 20.dp.toPx() })
                        }
                    )
                } else Modifier
            )
    ) {
        // Background Grid Layer
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridSize = 40.dp.toPx()
            val gridColor = GridLineColor

            for (x in 0..size.width.toInt() step gridSize.toInt()) {
                drawLine(
                    color = gridColor,
                    start = Offset(x.toFloat(), 0f),
                    end = Offset(x.toFloat(), size.height),
                    strokeWidth = 1f
                )
            }
            for (y in 0..size.height.toInt() step gridSize.toInt()) {
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y.toFloat()),
                    end = Offset(size.width, y.toFloat()),
                    strokeWidth = 1f
                )
            }

            // Glow lines
            for (x in 0..size.width.toInt() step (gridSize * 4).toInt()) {
                drawLine(
                    color = GlowLineColor,
                    start = Offset(x.toFloat(), 0f),
                    end = Offset(x.toFloat(), size.height),
                    strokeWidth = 2f
                )
            }
        }

        if (isLandscape) {
            LandscapeLayout(
                gameState = gameState,
                board = board,
                currentTetromino = currentTetromino,
                nextTetromino = nextTetromino,
                score = score,
                level = level,
                linesCleared = linesCleared,
                highScore = highScore,
                density = density,
                onDragMove = if (gameState == GameState.PLAYING) { delta ->
                    viewModel.onDragMove(delta, with(density) { 20.dp.toPx() })
                } else null,
                onStartGame = viewModel::startGame
            )
        } else {
            PortraitLayout(
                gameState = gameState,
                board = board,
                currentTetromino = currentTetromino,
                nextTetromino = nextTetromino,
                score = score,
                level = level,
                linesCleared = linesCleared,
                highScore = highScore,
                density = density,
                onDragMove = if (gameState == GameState.PLAYING) { delta ->
                    viewModel.onDragMove(delta, with(density) { 20.dp.toPx() })
                } else null,
                onStartGame = viewModel::startGame
            )
        }
    }
}

/**
 * 竖屏布局
 *
 * 组件从上到下依次为：计分板、游戏棋盘、游戏状态覆盖层
 */
@Composable
private fun PortraitLayout(
    gameState: GameState,
    board: com.texttetris.domain.model.GameBoard,
    currentTetromino: Tetromino?,
    nextTetromino: Tetromino?,
    score: Int,
    level: Int,
    linesCleared: Int,
    highScore: Int,
    density: androidx.compose.ui.unit.Density,
    onDragMove: ((Float) -> Unit)?,
    onStartGame: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Score display with integrated Next piece preview and control hints
        ScoreDisplay(
            score = score,
            level = level,
            linesCleared = linesCleared,
            highScore = highScore,
            nextTetromino = nextTetromino,
            showControlHints = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Game board - takes remaining space but respects aspect ratio
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false),
            contentAlignment = Alignment.Center
        ) {
            GameBoardCanvas(
                board = board,
                currentTetromino = currentTetromino,
                modifier = Modifier.fillMaxSize(),
                onDragMove = onDragMove
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Game state overlay (Start/Play Again button) at bottom center
        GameStateOverlay(
            gameState = gameState,
            score = score,
            onStartGame = onStartGame
        )
    }
}

/**
 * 横屏布局
 *
 * 左右分布：左侧计分板，中间游戏棋盘，右侧预览和按钮
 */
@Composable
private fun LandscapeLayout(
    gameState: GameState,
    board: com.texttetris.domain.model.GameBoard,
    currentTetromino: Tetromino?,
    nextTetromino: Tetromino?,
    score: Int,
    level: Int,
    linesCleared: Int,
    highScore: Int,
    density: androidx.compose.ui.unit.Density,
    onDragMove: ((Float) -> Unit)?,
    onStartGame: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScoreDisplay(
                score = score,
                level = level,
                linesCleared = linesCleared,
                highScore = highScore
            )
        }

        GameBoardCanvas(
            board = board,
            currentTetromino = currentTetromino,
            modifier = Modifier.weight(1f),
            onDragMove = onDragMove
        )

        Column(
            modifier = Modifier
                .weight(0.35f)
                .padding(start = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NextPiecePreview(tetromino = nextTetromino)
            Spacer(modifier = Modifier.height(8.dp))
            GameStateOverlay(
                gameState = gameState,
                score = score,
                onStartGame = onStartGame
            )
        }
    }
}

/**
 * 故障风格文字组件
 *
 * 通过偏移的红色和青色图层产生故障艺术效果
 *
 * @param text 显示的文本
 * @param modifier 修饰符
 * @param color 主颜色
 * @param fontSize 字体大小
 * @param textAlign 文字对齐方式
 */
@Composable
private fun GlitchText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = NeonCyan,
    fontSize: androidx.compose.ui.unit.TextUnit = 24.sp,
    textAlign: TextAlign? = null
) {
    Box(modifier = modifier) {
        // Glitch layers
        Text(
            text = text,
            color = NeonRed.copy(alpha = 0.5f),
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = textAlign,
            modifier = Modifier.offset(x = (-2).dp, y = 0.dp)
        )
        Text(
            text = text,
            color = NeonCyan.copy(alpha = 0.5f),
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = textAlign,
            modifier = Modifier.offset(x = 2.dp, y = 0.dp)
        )
        // Main text
        Text(
            text = text,
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = textAlign
        )
    }
}

/**
 * 游戏状态覆盖层
 *
 * 根据游戏状态显示不同的 UI：
 * - IDLE: 开始游戏按钮
 * - GAME_OVER: 游戏结束信息 + 重玩按钮
 * - PLAYING/PAUSED: 无显示
 */
@Composable
private fun GameStateOverlay(
    gameState: GameState,
    score: Int,
    onStartGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (gameState) {
        GameState.IDLE -> {
            CyberpunkButton(
                text = stringResource(R.string.btn_start_game),
                onClick = onStartGame,
                glowColor = NeonCyan
            )
        }
        GameState.GAME_OVER -> {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlitchText(
                    text = stringResource(R.string.game_over_text),
                    color = NeonRed,
                    fontSize = 24.sp,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.score_format, score),
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                CyberpunkButton(
                    text = stringResource(R.string.btn_play_again),
                    onClick = onStartGame,
                    glowColor = NeonGreen
                )
            }
        }
        GameState.PLAYING -> { }
        GameState.PAUSED -> { } // No pause UI - paused state is hidden
    }
}

/**
 * 赛博朋克风格按钮
 *
 * 发光边框的霓虹按钮
 *
 * @param text 按钮文字
 * @param onClick 点击回调
 * @param glowColor 发光颜色
 * @param modifier 修饰符
 */
@Composable
private fun CyberpunkButton(
    text: String,
    onClick: () -> Unit,
    glowColor: Color = NeonCyan,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .background(glowColor)
            .border(2.dp, glowColor, shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            color = Background,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
