package ru.ozh.compose.challenges.ui.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.ozh.compose.challenges.ui.grid.Icons.appleIcons
import ru.ozh.compose.challenges.ui.theme.ComposeChallengesTheme

@Composable
fun WatchGridLayout(
    modifier: Modifier = Modifier,
    rowItemsCount: Int,
    itemSize: Dp,
    state: WatchGridState = rememberGridLayoutState(),
    content: @Composable () -> Unit,
) {

    val itemSizePx = with(LocalDensity.current) { itemSize.roundToPx() }
    val itemConstraints = Constraints.fixed(width = itemSizePx, height = itemSizePx)

    Layout(
        modifier = modifier
            .drag(state)
            .clipToBounds(),
        content = content
    ) { measurables, layoutConstraints ->

        val placeables = measurables.map { measurable -> measurable.measure(itemConstraints) }
        val cells = placeables.mapIndexed { index, _ ->
            val x = index % rowItemsCount
            val y = (index - x) / rowItemsCount
            Cell(x, y)
        }

        state.setup(
            WatchGridConfig(
                layoutWidthPx = layoutConstraints.maxWidth,
                layoutHeightPx = layoutConstraints.maxHeight,
                itemSizePx = itemSizePx,
                cells = cells
            )
        )

        layout(layoutConstraints.maxWidth, layoutConstraints.maxHeight) {
            placeables.forEachIndexed { index, placeable ->
                val position = state.getPositionFor(index)
                val scale = state.getScaleFor(position)
                placeable.placeWithLayer(
                    position = position,
                    layerBlock = {
                        this.scaleX = scale
                        this.scaleY = scale
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WatchGridLayoutPreview() {
    ComposeChallengesTheme {
        WatchGridLayout(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Black),
            rowItemsCount = 5,
            itemSize = 80.dp
        ) {
            appleIcons.forEach { res ->
                Icon(
                    res = res
                )
            }
        }
    }
}