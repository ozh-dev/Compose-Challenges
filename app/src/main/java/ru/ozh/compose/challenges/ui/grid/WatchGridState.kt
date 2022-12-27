package ru.ozh.compose.challenges.ui.grid

import androidx.compose.ui.unit.IntOffset
import ru.ozh.compose.challenges.ui.ktx.OffsetState

interface WatchGridState : OffsetState {

    var config: WatchGridConfig

    fun getPositionFor(index: Int): IntOffset
    fun getScaleFor(position: IntOffset): Float
}