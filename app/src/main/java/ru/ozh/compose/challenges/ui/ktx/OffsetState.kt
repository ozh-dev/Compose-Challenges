package ru.ozh.compose.challenges.ui.ktx

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.ui.geometry.Offset

/**
 * @see [drag]
 */
interface OffsetState {

    val currentOffset: Offset
        get() = animatable.value

    val animatable: Animatable<Offset, AnimationVector2D>

    suspend fun snapTo(offset: Offset)
    suspend fun animateTo(offset: Offset, velocity: Offset)
    suspend fun stop()
}