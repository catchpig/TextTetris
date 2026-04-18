package com.texttetris.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.texttetris.data.local.GameRecordEntity
import com.texttetris.domain.model.GameBoard
import com.texttetris.domain.model.GameState
import com.texttetris.domain.model.Tetromino
import com.texttetris.domain.repository.GameRepository
import com.texttetris.domain.usecase.*
import com.texttetris.util.GameLogger
import com.texttetris.util.SoundManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 游戏 ViewModel
 *
 * 负责管理游戏状态、游戏逻辑协调
 * 管理方块的移动、旋转、掉落、消行等核心游戏玩法
 *
 * @param repository 游戏记录仓库，用于保存最高分等数据
 * @param soundManager 音效管理器，用于播放游戏音效
 */
class GameViewModel(
    private val repository: GameRepository,
    private val soundManager: SoundManager
) : ViewModel() {

    private val moveUseCase = MoveTetrominoUseCase()
    private val rotateUseCase = RotateTetrominoUseCase()
    private val spawnUseCase = SpawnTetrominoUseCase()
    private val lockUseCase = LockTetrominoUseCase()
    private val clearUseCase = ClearLinesUseCase()
    private val scoreUseCase = CalculateScoreUseCase()
    private val gameOverUseCase = CheckGameOverUseCase()

    /** 游戏状态 */
    private val _gameState = MutableStateFlow(GameState.IDLE)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    /** 游戏棋盘 */
    private val _board = MutableStateFlow(GameBoard.create())
    val board: StateFlow<GameBoard> = _board.asStateFlow()

    /** 当前下落的方块 */
    private val _currentTetromino = MutableStateFlow<Tetromino?>(null)
    val currentTetromino: StateFlow<Tetromino?> = _currentTetromino.asStateFlow()

    /** 下一个方块 */
    private val _nextTetromino = MutableStateFlow<Tetromino?>(null)
    val nextTetromino: StateFlow<Tetromino?> = _nextTetromino.asStateFlow()

    /** 当前分数 */
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    /** 当前等级 */
    private val _level = MutableStateFlow(1)
    val level: StateFlow<Int> = _level.asStateFlow()

    /** 已消除的总行数 */
    private val _linesCleared = MutableStateFlow(0)
    val linesCleared: StateFlow<Int> = _linesCleared.asStateFlow()

    /** 历史最高分 */
    private val _highScore = MutableStateFlow(0)
    val highScore: StateFlow<Int> = _highScore.asStateFlow()

    /** 游戏循环 Job */
    private var gameLoopJob: Job? = null

    /** 游戏开始时间（用于计算游戏时长） */
    private var gameStartTime: Long = 0

    init {
        viewModelScope.launch {
            _highScore.value = repository.getHighScore()
            GameLogger.i("GameViewModel initialized, highScore: ${_highScore.value}")
        }
    }

    /**
     * 开始新游戏
     *
     * 重置棋盘、分数、等级，生成新的方块
     */
    fun startGame() {
        GameLogger.i("Starting new game")

        _board.value = GameBoard.create()
        _score.value = 0
        _level.value = 1
        _linesCleared.value = 0
        _currentTetromino.value = spawnUseCase.spawn()
        _nextTetromino.value = spawnUseCase.spawn()
        _gameState.value = GameState.PLAYING
        gameStartTime = System.currentTimeMillis()
        startGameLoop()

        GameLogger.i("Game started, level: ${_level.value}")
    }

    /**
     * 暂停游戏
     */
    fun pauseGame() {
        GameLogger.i("Game paused, score: $_score, level: $_level")
        _gameState.value = GameState.PAUSED
        gameLoopJob?.cancel()
    }

    /**
     * 继续游戏
     */
    fun resumeGame() {
        GameLogger.i("Game resumed")
        _gameState.value = GameState.PLAYING
        startGameLoop()
    }

    /**
     * 向左移动方块
     */
    fun moveLeft() {
        _currentTetromino.value?.let { tetromino ->
            val newTetromino = moveUseCase.moveLeft(tetromino, _board.value)
            if (newTetromino != tetromino) {
                _currentTetromino.value = newTetromino
                soundManager.playMove()
                GameLogger.d("Moved left, position: (${newTetromino.position.x}, ${newTetromino.position.y})")
            }
        }
    }

    /**
     * 向右移动方块
     */
    fun moveRight() {
        _currentTetromino.value?.let { tetromino ->
            val newTetromino = moveUseCase.moveRight(tetromino, _board.value)
            if (newTetromino != tetromino) {
                _currentTetromino.value = newTetromino
                soundManager.playMove()
                GameLogger.d("Moved right, position: (${newTetromino.position.x}, ${newTetromino.position.y})")
            }
        }
    }

    /**
     * 旋转方块
     */
    fun rotate() {
        _currentTetromino.value?.let { tetromino ->
            val newTetromino = rotateUseCase.rotate(tetromino, _board.value)
            if (newTetromino != tetromino) {
                _currentTetromino.value = newTetromino
                soundManager.playRotate()
                GameLogger.d("Rotated, rotation: ${newTetromino.rotation}")
            }
        }
    }

    /**
     * 直接落地（快速下落）
     */
    fun drop() {
        _currentTetromino.value?.let { tetromino ->
            var current = tetromino
            var dropDistance = 0
            while (moveUseCase.canMoveDown(current, _board.value)) {
                current = moveUseCase.moveDown(current, _board.value)
                _currentTetromino.value = current
                dropDistance++
            }
            GameLogger.d("Dropped $dropDistance rows")
        }
        lockCurrentPiece()
    }

    /**
     * 处理拖拽移动（用于触摸手势）
     *
     * @param deltaPixels 拖拽像素距离
     * @param cellSizePx 每个方块单元的像素大小
     */
    fun onDragMove(deltaPixels: Float, cellSizePx: Float) {
        _currentTetromino.value?.let { tetromino ->
            val rowsDelta = (deltaPixels / cellSizePx).toInt()
            if (rowsDelta > 0) {
                val newTetromino = moveUseCase.moveDownBy(tetromino, _board.value, rowsDelta)
                if (newTetromino != tetromino) {
                    _currentTetromino.value = newTetromino
                }
            }
        }
    }

    /**
     * 启动游戏循环
     *
     * 根据等级调整下落间隔
     */
    private fun startGameLoop() {
        gameLoopJob?.cancel()
        gameLoopJob = viewModelScope.launch {
            while (true) {
                delay(getDropInterval().toLong())
                tick()
            }
        }
    }

    /**
     * 游戏滴答（每帧调用）
     *
     * 检查是否可以继续下落，否则锁定当前方块
     */
    private fun tick() {
        val tetromino = _currentTetromino.value ?: return

        if (moveUseCase.canMoveDown(tetromino, _board.value)) {
            _currentTetromino.value = moveUseCase.moveDown(tetromino, _board.value)
        } else {
            lockCurrentPiece()
        }
    }

    /**
     * 锁定当前方块到棋盘
     *
     * 执行消行检测、更新分数、生成下一个方块
     */
    private fun lockCurrentPiece() {
        val tetromino = _currentTetromino.value ?: return
        soundManager.playDrop()

        GameLogger.d("Locking piece at position: (${tetromino.position.x}, ${tetromino.position.y})")

        // 检查游戏是否结束（新方块无法生成）
        if (gameOverUseCase.isGameOver(tetromino, _board.value)) {
            GameLogger.w("Game over detected - new piece cannot spawn")
            endGame()
            return
        }

        // 将方块锁定到棋盘
        _board.value = lockUseCase.lock(tetromino, _board.value)

        // 执行消行
        val (newBoard, lines) = clearUseCase.clear(_board.value)
        _board.value = newBoard

        if (lines > 0) {
            GameLogger.i("Cleared $lines lines")
            _linesCleared.value += lines
            _score.value = scoreUseCase.addScore(_score.value, lines, _level.value)
            _level.value = scoreUseCase.levelUp(_linesCleared.value, _level.value)
            soundManager.playClear()
            GameLogger.i("Score updated: $_score, Level: $_level, Total lines: $_linesCleared")
        }

        // 生成下一个方块
        _currentTetromino.value = _nextTetromino.value
        _nextTetromino.value = spawnUseCase.spawn()

        // 检查下一个方块是否会导致游戏结束
        _currentTetromino.value?.let { current ->
            if (gameOverUseCase.isGameOver(current, _board.value)) {
                GameLogger.w("Game over detected - piece cannot spawn at top")
                endGame()
            }
        }
    }

    /**
     * 结束游戏
     */
    private fun endGame() {
        _gameState.value = GameState.GAME_OVER
        gameLoopJob?.cancel()
        soundManager.playGameOver()

        val finalScore = _score.value
        val finalLevel = _level.value
        val finalLines = _linesCleared.value
        val duration = System.currentTimeMillis() - gameStartTime

        GameLogger.e("GAME OVER - Score: $finalScore, Level: $finalLevel, Lines: $finalLines, Duration: ${duration}ms")

        viewModelScope.launch {
            val record = GameRecordEntity(
                score = finalScore,
                level = finalLevel,
                linesCleared = finalLines,
                duration = duration,
                playedAt = System.currentTimeMillis()
            )
            repository.saveGameRecord(record)
            _highScore.value = repository.getHighScore()
            GameLogger.i("Game record saved, new highScore: ${_highScore.value}")
        }
    }

    /**
     * 根据等级获取下落间隔
     *
     * 等级越高，间隔越短
     */
    private fun getDropInterval(): Int {
        return when {
            _level.value >= 15 -> 100
            _level.value >= 10 -> 200
            _level.value >= 5 -> 400
            else -> 800
        }
    }

    override fun onCleared() {
        super.onCleared()
        GameLogger.i("GameViewModel cleared")
        soundManager.release()
    }
}
