package com.example.start_wars.ui.base.common

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class DataClassCardStyle(
    val elevation: Dp,
    val borderWidth: Dp,
    val borderColor: Color,
    val backgroundColor: Color,
    val contentColor: Color,
    val cornerRadius: Dp
    )

val CardStyle = DataClassCardStyle(
    elevation = 5.dp,
    borderWidth = 1.dp,
    borderColor = Color(0xFFFFE81F),
    backgroundColor = Color(0xFF1E1E1E),
    contentColor = Color(0xFFFFFFFF),
    cornerRadius = 5.dp
)

val LocalCardStyle = staticCompositionLocalOf { CardStyle }