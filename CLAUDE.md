# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

TextTetris（文字俄罗斯方块）是一款基于 Jetpack Compose 的 Android 俄罗斯方块游戏，采用大字符艺术风格呈现。

## 常用命令

### 构建和安装
```bash
# 调试构建
./gradlew assembleDebug

# 安装到已连接设备
adb install -r app/build/outputs/apk/debug/app-debug.apk

# 清理并重新构建
./gradlew clean assembleDebug

# 在设备上启动应用
adb shell am start -n com.texttetris/.MainActivity

# 查看应用日志
adb logcat -d | grep texttetris
```

### 依赖管理
- 使用腾讯云 Maven 镜像加速下载（`settings.gradle.kts`）
- KSP 用于 Room 注解处理器

## 架构设计

### 三层架构 (MVVM + DDD)

```
UI Layer (ui/)
├── screens/     - GameScreen 游戏主界面
├── components/  - 可复用组件（GameBoardCanvas、NextPiecePreview、GestureDetector）
├── viewmodel/   - GameViewModel 状态管理
└── theme/      - Compose 主题配置

Domain Layer (domain/)
├── model/       - 领域模型（Tetromino、GameBoard、GameState、Position）
├── usecase/     - 用例逻辑（移动、旋转、消行、计分）
└── repository/  - 仓库接口

Data Layer (data/)
├── local/       - Room 数据库（GameDatabase、GameRecordEntity、GameRecordDao）
└── repository/  - 仓库实现（GameRepositoryImpl）
```

### 核心领域模型

- **Tetromino**: 7种方块形状（I、O、T、S、Z、J、L），包含旋转状态
- **GameBoard**: 10×20 棋盘，用 `List<MutableList<Cell>>` 表示
- **GameState**: 游戏状态（IDLE、PLAYING、PAUSED、GAME_OVER）
- **Position**: (x, y) 坐标，定义方块位置

### 手势控制

通过 `GestureDetector.kt` 中的 Modifier 扩展函数实现：
- 左滑 → 向左移动
- 右滑 → 向右移动
- 上滑 → 旋转
- 下滑 → 快速下落
- 双击 → 暂停/继续

## 技术栈

- **Kotlin 2.1.0** + **Jetpack Compose**
- **Room 2.6.1** 数据库（使用 KSP 注解处理）
- **StateFlow** 响应式状态管理
- **SoundPool** 音效播放
- **Compose Canvas** 自定义绘制

## 注意事项

- `domain/model/TetrominoColor.kt` 中的 `toComposeColor()` 是跨层接口，返回 Compose `Color` 对象
- Room 的 KSP 配置在 `app/build.gradle.kts` 的 `defaultConfig` 块中
- 游戏循环使用 `viewModelScope.launch` + `delay()` 实现
- 方块旋转带有 wall kick 补偿逻辑

## 语言规范

- 所有回复和注释统一使用中文
- 代码注释使用中文说明业务逻辑
