package ru.ozh.compose.challenges.ui.switch

import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

internal object SwitchConsts {

    val RippleRadius = 20.0.dp
    val SwitchWidth = 60.0.dp
    val SwitchHeight = 32.0.dp
    val ThumbDiameter = 24.0.dp
    val ThumbStartOffset = 4.dp
    val ThumbPadding = (SwitchHeight - ThumbDiameter) / 2
    val ThumbPathLength = (SwitchWidth - ThumbDiameter) - ThumbPadding

    val Black = Color(red = 53, green = 53, blue = 53)
    val Orange = Color(red = 243, green = 109, blue = 24)
}