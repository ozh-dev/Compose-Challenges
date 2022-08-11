package ru.ozh.compose.challenges.ui.grid

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

interface WatchGridState {

    val currentOffset: Offset
    val animatable: Animatable<Offset, AnimationVector2D>

    suspend fun snapTo(value: Offset)
    suspend fun animateTo(value: Offset, velocity: Offset)
    suspend fun stop()

    fun getPositionFor(index: Int): IntOffset
    fun getScaleFor(position: IntOffset): Float
    fun setup(config: WatchGridConfig)
}