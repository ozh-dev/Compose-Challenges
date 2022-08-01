package ru.ozh.compose.challenges.ui.grid

import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.positionChangeConsumed
import androidx.compose.ui.input.pointer.util.VelocityTracker
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun Modifier.drag(
    state: WatchGridState
) = pointerInput(Unit) {
    val decay = splineBasedDecay<Offset>(this)
    val tracker = VelocityTracker()
    coroutineScope {
        while (true) {
            val pointerId = awaitPointerEventScope { awaitFirstDown().id }
            state.stop()
            tracker.resetTracking()
            awaitPointerEventScope {
                drag(pointerId) { change ->
                    change.positionChangeConsumed()
                    launch {
                        state.snapTo(state.currentOffset.plus(change.positionChange()))
                    }
                    tracker.addPosition(change.uptimeMillis, change.position)
                }
            }
            val velocity = Offset(
                tracker.calculateVelocity().x,
                tracker.calculateVelocity().y
            )
            val targetValue = decay.calculateTargetValue(
                typeConverter = Offset.VectorConverter,
                initialValue = state.currentOffset,
                initialVelocity = velocity
            )
            launch {
                state.animateTo(
                    velocity = velocity,
                    value = targetValue
                )
            }
        }
    }
}