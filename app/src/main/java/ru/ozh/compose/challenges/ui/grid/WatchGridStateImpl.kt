package ru.ozh.compose.challenges.ui.grid

import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

data class WatchGridStateImpl(
    private val initialOffset: Offset = Offset.Zero
) : WatchGridState {

    companion object {
        val Saver = Saver<WatchGridStateImpl, List<Float>>(
            save = {
                val (x, y) = it.currentOffset
                listOf(x, y)
            },
            restore = {
                WatchGridStateImpl(initialOffset = Offset(it[0], it[1]))
            }
        )

        private const val CELL_MIN_SCALE = 0.5f
        private const val CELL_MAX_SCALE = 1.0f
    }

    private val decayAnimationSpec = SpringSpec<Offset>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
    )

    override val animatable = Animatable(
        initialValue = initialOffset,
        typeConverter = Offset.VectorConverter
    )

    override var config: WatchGridConfig = WatchGridConfig()

    override suspend fun snapTo(offset: Offset) {
        val x = offset.x.coerceIn(config.overScrollDragRangeHorizontal)
        val y = offset.y.coerceIn(config.overScrollDragRangeVertical)
        animatable.snapTo(Offset(x, y))
    }

    override suspend fun animateTo(offset: Offset, velocity: Offset) {
        val x = offset.x.coerceIn(config.overScrollRangeHorizontal)
        val y = offset.y.coerceIn(config.overScrollRangeVertical)
        animatable.animateTo(
            initialVelocity = velocity,
            animationSpec = decayAnimationSpec,
            targetValue = Offset(x, y)
        )
    }

    override suspend fun stop() {
        animatable.stop()
    }

    override fun getPositionFor(index: Int): IntOffset {
        val (offsetX, offsetY) = currentOffset
        val (cellX, cellY) = config.cells[index]
        val rowOffset = if (cellY % 2 != 0) {
            config.halfItemSizePx
        } else {
            0
        }
        val x = (cellX * config.itemSizePx) + offsetX.toInt() + rowOffset
        val y = (cellY * config.itemSizePx) + offsetY.toInt()

        return IntOffset(x, y)
    }

    override fun getScaleFor(position: IntOffset): Float {
        val (centerX, centerY) = position.plus(
            IntOffset(
                config.halfItemSizePx,
                config.halfItemSizePx
            )
        )
        val offsetX = centerX - config.layoutCenter.x
        val offsetY = centerY - config.layoutCenter.y
        val x = (offsetX * offsetX) / (config.a * config.a)
        val y = (offsetY * offsetY) / (config.b * config.b)
        val z = (-config.c * (x + y) + 1.1f)
            .coerceIn(
                minimumValue = CELL_MIN_SCALE,
                maximumValue = CELL_MAX_SCALE
            )
        return z
    }
}

@Composable
fun rememberWatchGridState(): WatchGridState {
    return rememberSaveable(saver = WatchGridStateImpl.Saver) {
        WatchGridStateImpl()
    }
}