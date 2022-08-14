package ru.ozh.compose.challenges.ui.grid

import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.input.pointer.util.addPointerInputChange
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun Modifier.drag(state: WatchGridState) = pointerInput(Unit) {
    val decay = splineBasedDecay<Offset>(this)
    val tracker = VelocityTracker()
    coroutineScope {
        forEachGesture {
            awaitPointerEventScope {
                val pointerId = awaitFirstDown(requireUnconsumed = false).id
                launch {
                    state.stop()
                }
                tracker.resetTracking()
                var dragPointerInput: PointerInputChange?
                var overSlop = Offset.Zero
                do {
                    dragPointerInput = awaitTouchSlopOrCancellation(
                        pointerId
                    ) { change, over ->
                        change.consumePositionChange()
                        overSlop = over
                    }
                } while (dragPointerInput != null && !dragPointerInput.positionChangeConsumed())

                dragPointerInput?.let {

                    launch {
                        state.snapTo(state.currentOffset.plus(overSlop))
                    }

                    drag(dragPointerInput.id) { change ->
                        val dragAmount = change.positionChange()
                        launch {
                            state.snapTo(state.currentOffset.plus(dragAmount))
                        }
                        change.consumePositionChange()
                        tracker.addPointerInputChange(change)
                    }
                }
            }

            val (velX, velY) = tracker.calculateVelocity()
            val velocity = Offset(velX, velY)
            val targetOffset = decay.calculateTargetValue(
                typeConverter = Offset.VectorConverter,
                initialValue = state.currentOffset,
                initialVelocity = velocity
            )
            launch {
                state.animateTo(
                    offset = targetOffset,
                    velocity = velocity,
                )
            }
        }
    }
}