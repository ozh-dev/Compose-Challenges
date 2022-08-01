package ru.ozh.compose.challenges.ui.grid

import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

data class GridLayoutStateImpl(
    private val initialOffset: Offset = Offset.Zero
) : WatchGridState {

    companion object {
        val Saver = Saver<GridLayoutStateImpl, List<Float>>(
            save = {
                val (x, y) = it.currentOffset
                listOf(x, y)
            },
            restore = {
                GridLayoutStateImpl(Offset(it[0], it[1]))
            }
        )
    }

    private var config: WatchGridConfig = WatchGridConfig()

    private val decayAnimationSpec = SpringSpec<Offset>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
    )

    override val animatable = Animatable(
        initialValue = initialOffset,
        typeConverter = Offset.VectorConverter
    )

    override val currentOffset: Offset
        get() = animatable.value

    override suspend fun snapTo(value: Offset) {
        val x = value.x.coerceIn(config.overScrollDragRangeHorizontal)
        val y = value.y.coerceIn(config.overScrollDragRangeVertical)
        animatable.snapTo(Offset(x, y))
    }

    override suspend fun animateTo(velocity: Offset, value: Offset) {
        val x = value.x.coerceIn(config.overScrollRangeHorizontal)
        val y = value.y.coerceIn(config.overScrollRangeVertical)
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
        var x = (cellX * config.itemSizePx) + offsetX.toInt()
        val y = (cellY * config.itemSizePx) + offsetY.toInt()

        if (cellY % 2 != 0) {
            x += config.halfItemSizePx
        }

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
            .coerceIn(minimumValue = 0.5f, maximumValue = 1f)
        return z
    }

    override fun setup(config: WatchGridConfig) {
        this.config = config
    }
}

@Composable
fun rememberGridLayoutState(): WatchGridState {
    return rememberSaveable(saver = GridLayoutStateImpl.Saver) {
        GridLayoutStateImpl()
    }
}